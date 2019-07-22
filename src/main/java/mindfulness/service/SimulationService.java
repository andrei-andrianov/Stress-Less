package mindfulness.service;

import com.mathworks.engine.MatlabEngine;
import lombok.extern.slf4j.Slf4j;
import mindfulness.exception.UserNotFoundException;
import mindfulness.model.SimulationType;
import mindfulness.model.Simulation;
import mindfulness.model.User;
import mindfulness.repository.UserRepository;
import org.springframework.stereotype.Service;

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
import java.util.stream.Collectors;

@Slf4j
@Service
public class SimulationService {

    private final UserRepository userRepository;

    public SimulationService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public SimulationType suggestSimulation(String userId){
        log.debug("Suggesting a simulation type..");
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        Map<SimulationType, Float> userPreferences = new HashMap<>();
        userPreferences.put(SimulationType.MINDFULNESS, user.getMindfulness() != null ? user.getMindfulness() : 0);
        userPreferences.put(SimulationType.HUMOUR, user.getHumour() != null ? user.getHumour() : 0);
        userPreferences.put(SimulationType.MUSIC, user.getMusic() != null ? user.getMusic() : 0);

        return Collections.max(userPreferences.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public String generateFilename(Simulation simulation){
        log.debug("Generating simulation filename..");
        String filename = new StringBuilder()
                .append(simulation.getUser().getId())
                .append("_")
                .append(simulation.getSimulationType())
                .append("_")
                .append(simulation.getTimestamp().getTime())
                .toString();
        return filename;
    }

//    run matlab simulation asynchronously
    public void runSimulation(Simulation simulation) {
//        Declare an Engine future object and an Engine object.
//        Future object is used to return the result in case of an asynchronous call.
        Future<MatlabEngine> matlabEngineFuture = MatlabEngine.startMatlabAsync();
        log.debug("MATLAB started asynchronously.");

//        save simulation parameters to file
        saveParameters(simulation);

        try {
//            Get the MATLAB engine object from the future.
            MatlabEngine matlabEngine = matlabEngineFuture.get();

            Future<Void> simulationFuture = new CompletableFuture<>();

            matlabEngine.putVariableAsync("filename", simulation.getFileName().toCharArray());

            switch (simulation.getSimulationType()){
                case MINDFULNESS:
                    log.debug("Running mindfulness simulation..");
                    simulationFuture = matlabEngine.evalAsync(new String(
                            Files.readAllBytes(Paths.get("simulation/simulation_mindfulness.m")), StandardCharsets.UTF_8));
                    break;
                case HUMOUR:
                    log.debug("Running humour simulation..");
                    simulationFuture = matlabEngine.evalAsync("humour script");
                    break;
                case MUSIC:
                    log.debug("Running music simulation..");
                    simulationFuture = matlabEngine.evalAsync("music script");
                    break;
            }

//            TODO Close the matlab connection
            if (simulationFuture.isDone()) {
                log.debug("Simulation run has been finished, disconnecting MATLAB..");
                matlabEngine.disconnectAsync();
            }

        } catch (ExecutionException | InterruptedException | IOException e){
            log.error("Runtime error while starting a simulation", e);
        }
    }

    private void saveParameters(Simulation simulation){
        log.debug("Saving simulation parameters to file..");
        List<Float> simulationParameters = new ArrayList<>();
        simulationParameters.add(
                simulation.getUser().getStressEvent() != null ? simulation.getUser().getStressEvent() : 0);
        simulationParameters.add(
                simulation.getUser().getStressLevel() != null ? simulation.getUser().getStressLevel() : 0);
        simulationParameters.add(
                simulation.getUser().getPositiveBelief() != null ? simulation.getUser().getPositiveBelief() : 0);
        simulationParameters.add(
                simulation.getUser().getNegativeBelief() != null ? simulation.getUser().getNegativeBelief() : 0);

        String simulationParametersString = simulationParameters.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

//        File with global settings for a simulation run
//        Local file needed just to store parameters
        Path simulationParametersFileGlobal = Paths.get("simulation/params.csv");
        Path simulationParametersFileLocal = Paths.get("simulation/data/" + simulation.getFileName() + "_params.csv");

        String simulationParametersHeader = "stressEvent,stressLevel,positiveBelief,negativeBelief\n";

        try{
            Files.write(simulationParametersFileGlobal, simulationParametersHeader.getBytes());
            Files.write(simulationParametersFileGlobal, simulationParametersString.getBytes(), StandardOpenOption.APPEND);

            Files.write(simulationParametersFileLocal, simulationParametersHeader.getBytes());
            Files.write(simulationParametersFileLocal, simulationParametersString.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e){
            log.error("Error occurred while saving simulation parameters", e);
        }
    }

//    TODO implement fetching stress level for the past 14 days
    public List<Float> getStressLevel(String userId){
        log.debug("Getting stress level for the last 14 days..");
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        user.setGender("transformer");

        List<Float> stressLevel = new ArrayList<>();
        for (int i = 1; i < 15; i++)
            stressLevel.add((float)i);

        return stressLevel;
    }
}