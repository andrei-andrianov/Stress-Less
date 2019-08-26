package mindfulness.service;

import com.mathworks.engine.MatlabEngine;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import mindfulness.exception.NotEnoughSimulationsException;
import mindfulness.exception.UserNotFoundException;
import mindfulness.model.SimulationType;
import mindfulness.model.Simulation;
import mindfulness.model.User;
import mindfulness.repository.SimulationRepository;
import mindfulness.repository.UserRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SimulationService {

    private final UserRepository userRepository;
    private final SimulationRepository simulationRepository;
//    Min number of recent simulations to take into account for parameters tuning and therapy suggestion
    private final Integer MIN_NUMBER_OF_RECENT_SIMULATIONS = 5;

    public SimulationService(UserRepository userRepository, SimulationRepository simulationRepository)
    {
        this.userRepository = userRepository;
        this.simulationRepository = simulationRepository;
    }

//    Get list of recent simulations for a user
    private List<Simulation> getRecentSimulations(String userId, Integer numberOfRecentSimulations){
        List<Simulation> simulationList = simulationRepository.findByUserId(userId);

        if (!simulationList.isEmpty()){
//        Sort simulations by timestamp
            simulationList.sort((s1, s2) -> {
                if (s1.getTimestamp() == s2.getTimestamp())
                    return 0;
                return s1.getTimestamp().getTime() > s2.getTimestamp().getTime() ? -1 : 1;
            });

//        Leave only the most recent simulations
            simulationList = simulationList.stream()
                    .limit(numberOfRecentSimulations)
                    .collect(Collectors.toList());
        }

        return simulationList;
    }

    public SimulationType suggestSimulation(String userId){
        log.debug("Suggesting a simulation type..");
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        SimulationType suggestedSimulation;

//        Map to store user preferences
        Map<SimulationType, Float> userPreferences = new LinkedHashMap<>();
        userPreferences.put(SimulationType.MINDFULNESS, user.getMindfulness() != null ? user.getMindfulness() : 0);
        userPreferences.put(SimulationType.HUMOUR, user.getHumour() != null ? user.getHumour() : 0);
        userPreferences.put(SimulationType.MUSIC, user.getMusic() != null ? user.getMusic() : 0);

//        Sort user preferences
        userPreferences = userPreferences.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

//        Get list of simulations for a user
        List<Simulation> simulationList = getRecentSimulations(userId, MIN_NUMBER_OF_RECENT_SIMULATIONS);

        if (!(simulationList.size() < MIN_NUMBER_OF_RECENT_SIMULATIONS)){
            List<Map<SimulationType, Float>> simulationParams = new ArrayList<>();

//            Read simulation parameters from file
            for (Simulation simulation : simulationList) {
                List<List<String>> simulationParamsFileList = new ArrayList<>();

                try (CSVReader csvReader = new CSVReader(
                        new FileReader("simulation/data/" + simulation.getFileName() + "_params.csv"))) {

                    String[] line;
                    while ((line = csvReader.readNext()) != null)
                        simulationParamsFileList.add(Arrays.asList(line));

                    Map<SimulationType, Float> singleSimulationParams = new LinkedHashMap<>();
                    singleSimulationParams.put(simulation.getSimulationType(),
                            Float.parseFloat(simulationParamsFileList.get(1).get(1)));

                    simulationParams.add(singleSimulationParams);
                } catch (IOException e) {
                    log.error("Could not read simulation params file", e);
                }
            }

//            Simulation params to compare
            Map<SimulationType, Float> firstSimulationParams = simulationParams.get(0);
            Map<SimulationType, Float> lastSimulationParams = simulationParams.get(4);

//            Check if the stress level has decreased
            if (lastSimulationParams.entrySet().iterator().next().getValue() >=
                    firstSimulationParams.entrySet().iterator().next().getValue())
//                If the stress level has not decreased and the simulation type has not changed,
//                                suggest different simulation type
                if (lastSimulationParams.entrySet().iterator().next().getKey() ==
                        firstSimulationParams.entrySet().iterator().next().getKey()){
                    SimulationType currentSimulationType = lastSimulationParams.entrySet().iterator().next().getKey();

                    userPreferences.remove(currentSimulationType);
                    suggestedSimulation = userPreferences.entrySet().iterator().next().getKey();
                } else {
                    suggestedSimulation = lastSimulationParams.entrySet().iterator().next().getKey();
                }
            else
                suggestedSimulation = lastSimulationParams.entrySet().iterator().next().getKey();
//            If no previous simulations found, suggest based on the highest preference
        } else
            suggestedSimulation = Collections.max(
                    userPreferences.entrySet(), Map.Entry.comparingByValue()).getKey();

        return suggestedSimulation;
    }

    public String generateFilename(Simulation simulation){
        log.debug("Generating simulation filename..");
        final String filename = new StringBuilder()
                .append(simulation.getUser().getId())
                .append("_")
                .append(simulation.getSimulationType())
                .append("_")
                .append(simulation.getTimestamp().getTime())
                .toString();
        return filename;
    }

    @Async
    public Future<Void> tuneParameters(MatlabEngine matlabEngine){
        Future<Void> parameterTuningFuture = new CompletableFuture<>();

        try{
            log.debug("Tuning parameters for the simulation..");
            parameterTuningFuture = matlabEngine.evalAsync(new String(
                    Files.readAllBytes(Paths.get("simulation/simulanneal.m")), StandardCharsets.UTF_8));
        } catch (IOException e){
            log.error("Runtime error while running parameter tuning", e);
        }

        return parameterTuningFuture;
    }

//    Run matlab simulation asynchronously
    public void runSimulation(Simulation simulation, boolean isTuningOn) {
//        Declare an Engine future object and an Engine object.
//        Future object is used to return the result in case of an asynchronous call.
        Future<MatlabEngine> matlabEngineFuture = MatlabEngine.startMatlabAsync();
        log.debug("MATLAB started asynchronously.");

        saveParameters(simulation);

        try {
//            Get the MATLAB engine object from the future.
            MatlabEngine matlabEngine = matlabEngineFuture.get();

            Future<Void> simulationFuture = new CompletableFuture<>();

//            Pass filename to matlab
            matlabEngine.putVariableAsync("filename", simulation.getFileName().toCharArray());

            switch (simulation.getSimulationType()){
                case MINDFULNESS:
                    log.debug("Running mindfulness simulation..");
                    simulationFuture = matlabEngine.evalAsync(new String(
                            Files.readAllBytes(Paths.get("simulation/simulation_mindfulness.m")), StandardCharsets.UTF_8));

                    if (isTuningOn){
                        TimeUnit.SECONDS.sleep(90);
                        Future<Void> parameterTuningFuture = tuneParameters(matlabEngine);

                        while (true)
                            if (parameterTuningFuture.isDone())
                                break;

                        simulationFuture = matlabEngine.evalAsync(new String(
                                Files.readAllBytes(Paths.get("simulation/simulation_mindfulness.m")), StandardCharsets.UTF_8));
                    }

                    break;
                case HUMOUR:
                    log.debug("Running humour simulation..");
                    simulationFuture = matlabEngine.evalAsync(new String(
                            Files.readAllBytes(Paths.get("simulation/simulation_humour.m")), StandardCharsets.UTF_8));

                    if (isTuningOn){
                        TimeUnit.SECONDS.sleep(90);
                        Future<Void> parameterTuningFuture = tuneParameters(matlabEngine);

                        while (true)
                            if (parameterTuningFuture.isDone())
                                break;

                        simulationFuture = matlabEngine.evalAsync(new String(
                                Files.readAllBytes(Paths.get("simulation/simulation_humour.m")), StandardCharsets.UTF_8));
                    }

                    break;
                case MUSIC:
                    log.debug("Running music simulation..");
                    simulationFuture = matlabEngine.evalAsync(new String(
                            Files.readAllBytes(Paths.get("simulation/simulation_music.m")), StandardCharsets.UTF_8));

                    if (isTuningOn){
                        TimeUnit.SECONDS.sleep(90);
                        Future<Void> parameterTuningFuture = tuneParameters(matlabEngine);

                        while (true)
                            if (parameterTuningFuture.isDone())
                                break;

                        simulationFuture = matlabEngine.evalAsync(new String(
                                Files.readAllBytes(Paths.get("simulation/simulation_music.m")), StandardCharsets.UTF_8));
                    }

                    break;
            }

//            TODO Close the matlab connection
            if (simulationFuture.isDone()) {
                log.debug("Simulation run has been finished, disconnecting MATLAB..");
                matlabEngine.disconnectAsync();
            }

        } catch (ExecutionException | InterruptedException | IOException e){
            log.error("Runtime error while running a simulation", e);
        }
    }

    /**
     * averaging simulanneal results
     * @param simulation
     *
     */
    private List<Float> exponentialMovingAverage(Simulation simulation){
        List<Float> simulationParamsTuned;
        List<Float> currentSimulationParams = new ArrayList<>();
        currentSimulationParams.add(simulation.getDefParam1());
        currentSimulationParams.add(simulation.getDefParam2());
        currentSimulationParams.add(simulation.getDefParam3());

//        Here goes the parameters tuning part
        List<Simulation> simulationList = getRecentSimulations(simulation.getUser().getId(), MIN_NUMBER_OF_RECENT_SIMULATIONS);

        if (!(simulationList.size() < MIN_NUMBER_OF_RECENT_SIMULATIONS)){
            log.debug("Tuning simulation parameters..");
            List<List<Float>> simulationParamsListTuned = new ArrayList<>();

            for (int i = 0; i < MIN_NUMBER_OF_RECENT_SIMULATIONS; i++){
                List<List<String>> simulationParamsFileListTuned = new ArrayList<>();

                try (CSVReader csvReader = new CSVReader(
                        new FileReader("simulation/data/" + simulationList.get(i).getFileName() + "_tuned.csv"))){

                    String[] line;
                    while ((line = csvReader.readNext()) != null)
                        simulationParamsFileListTuned.add(Arrays.asList(line));

                    simulationParamsListTuned.add(simulationParamsFileListTuned.get(1).stream()
                            .map(Float::valueOf)
                            .collect(Collectors.toList()));

                } catch (IOException e){
                    log.error("Could not read simulation params file", e);
                }
            }

//            Exponential moving average calculation
            List<Float> parametersTuningResult = new ArrayList<>();
            currentSimulationParams = simulationParamsListTuned.get(simulationParamsListTuned.size() - 1);
            List<Float> lastSimulationParamsTuned = simulationParamsListTuned.get(simulationParamsListTuned.size() - 2);

            for (int i = 0; i < 3; i++)
                parametersTuningResult.add((currentSimulationParams.get(i) - lastSimulationParamsTuned.get(i)) *
                        ((float)2/((float) MIN_NUMBER_OF_RECENT_SIMULATIONS + (float)1)) + lastSimulationParamsTuned.get(i));

            simulationParamsTuned = parametersTuningResult;
        } else
//            Using default parameters
            simulationParamsTuned = currentSimulationParams;

        return simulationParamsTuned;
    }

//    Simulation parameters tuning and saving
    private void saveParameters(Simulation simulation){
        log.debug("Saving simulation parameters to file..");
        List<Float> simulationParams;
        List<Float> simulationParamsTuned;
        List<Float> currentSimulationParams = new ArrayList<>();
        currentSimulationParams.add(
                simulation.getUser().getStressEvent() != null ? simulation.getUser().getStressEvent() : 0);
        currentSimulationParams.add(
                simulation.getUser().getStressLevel() != null ? simulation.getUser().getStressLevel() : 0);
        currentSimulationParams.add(
                simulation.getUser().getPositiveBelief() != null ? simulation.getUser().getPositiveBelief() : 0);
        currentSimulationParams.add(
                simulation.getUser().getNegativeBelief() != null ? simulation.getUser().getNegativeBelief() : 0);


//        Here goes the parameters tuning part
        List<Simulation> simulationList = getRecentSimulations(simulation.getUser().getId(), MIN_NUMBER_OF_RECENT_SIMULATIONS);

        if (!(simulationList.size() < MIN_NUMBER_OF_RECENT_SIMULATIONS)){
            log.debug("Tuning user simulation parameters..");
            List<List<Float>> simulationParamsList = new ArrayList<>();

            for (int i = 0; i < MIN_NUMBER_OF_RECENT_SIMULATIONS; i++){
                List<List<String>> simulationParamsFileList = new ArrayList<>();

                try (CSVReader csvReader = new CSVReader(
                        new FileReader("simulation/data/" + simulationList.get(i).getFileName() + "_params.csv"))){

                    String[] line;
                    while ((line = csvReader.readNext()) != null)
                        simulationParamsFileList.add(Arrays.asList(line));

                    simulationParamsList.add(simulationParamsFileList.get(1).stream()
                            .map(Float::valueOf)
                            .collect(Collectors.toList()));

                } catch (IOException e){
                    log.error("Could not read simulation params file", e);
                }
            }

//            Exponential moving average calculation
            List<Float> parametersTuningResult = new ArrayList<>();
            List<Float> lastSimulationParams = simulationParamsList.get(simulationParamsList.size() - 1);

            for (int i = 0; i < 4; i++)
                parametersTuningResult.add((currentSimulationParams.get(i) - lastSimulationParams.get(i)) *
                        ((float)2/((float) MIN_NUMBER_OF_RECENT_SIMULATIONS + (float)1)) + lastSimulationParams.get(i));

            simulationParams = parametersTuningResult;
        } else
            simulationParams = currentSimulationParams;

        String simulationParametersString = simulationParams.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

        simulationParamsTuned = exponentialMovingAverage(simulation);
        String simulationParametersTunedString = simulationParamsTuned.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

//        File with global settings for a simulation run
//        Local file needed just to store parameters
        Path simulationParametersFileGlobal = Paths.get("simulation/params.csv");
        Path simulationParametersFileLocal = Paths.get("simulation/data/" + simulation.getFileName() + "_params.csv");
        Path simulationParametersTunedFileLocal = Paths.get("simulation/data/" + simulation.getFileName() + "_tuned.csv");

        String simulationParametersHeader = "stressEvent,stressLevel,positiveBelief,negativeBelief\n";
        String simulationParametersTunedHeader = "wsee,fsee,esee\n";

        try{
            Files.write(simulationParametersFileGlobal, simulationParametersHeader.getBytes());
            Files.write(simulationParametersFileGlobal, simulationParametersString.getBytes(), StandardOpenOption.APPEND);

            Files.write(simulationParametersFileLocal, simulationParametersHeader.getBytes());
            Files.write(simulationParametersFileLocal, simulationParametersString.getBytes(), StandardOpenOption.APPEND);

            Files.write(simulationParametersTunedFileLocal, simulationParametersTunedHeader.getBytes());
            Files.write(simulationParametersTunedFileLocal, simulationParametersTunedString.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e){
            log.error("Error occurred while saving simulation parameters", e);
        }
    }

    public List<Float> getStressLevel(String userId){
        log.debug("Getting stress level for the last 14 days..");
        List<Simulation> simulationList = getRecentSimulations(userId, 14);

        if (!(simulationList.size() < MIN_NUMBER_OF_RECENT_SIMULATIONS)){
            List<Float> stressLevels = new ArrayList<>();

//            Read simulation parameters from file
            for (Simulation simulation : simulationList) {
                List<List<String>> simulationParamsFileList = new ArrayList<>();

                try (CSVReader csvReader = new CSVReader(
                        new FileReader("simulation/data/" + simulation.getFileName() + "_params.csv"))) {

                    String[] line;
                    while ((line = csvReader.readNext()) != null)
                        simulationParamsFileList.add(Arrays.asList(line));

                    Float singleStressLevel = Float.parseFloat(simulationParamsFileList.get(1).get(1));

                    stressLevels.add(singleStressLevel);
                } catch (IOException e) {
                    log.error("Could not read simulation params file", e);
                }
            }

            return stressLevels;

        } else
            throw new NotEnoughSimulationsException(userId, simulationList.size(), MIN_NUMBER_OF_RECENT_SIMULATIONS);
    }
}