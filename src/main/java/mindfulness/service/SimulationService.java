package mindfulness.service;

import com.mathworks.engine.MatlabEngine;
import lombok.extern.slf4j.Slf4j;
import mindfulness.exception.UserNotFoundException;
import mindfulness.model.SimulationType;
import mindfulness.model.Simulation;
import mindfulness.model.User;
import mindfulness.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        userPreferences.put(SimulationType.MINDFULNESS, user.getMindfulness());
        userPreferences.put(SimulationType.HUMOUR, user.getHumour());
        userPreferences.put(SimulationType.MUSIC, user.getMusic());

        SimulationType simulationType = Collections.max(userPreferences.entrySet(), Map.Entry.comparingByValue()).getKey();

        return SimulationType.MINDFULNESS;
    }

    public String generateFilename(Simulation simulation){
        log.debug("Generating simulation filename..");
        String filename = new StringBuilder()
                .append(simulation.getUser().getId())
                .append("-")
                .append(simulation.getSimulationType())
                .append("-")
                .append(simulation.getTimestamp())
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

            switch (simulation.getSimulationType()){
                case MINDFULNESS:
                    log.debug("Starting mindfulness simulation..");
                    simulationFuture = matlabEngine.evalAsync(new String(
                            Files.readAllBytes(Paths.get("simulation_mindfulness.m")), StandardCharsets.UTF_8));
                    break;
                case HUMOUR:
                    log.debug("Starting humour simulation..");
                    simulationFuture = matlabEngine.evalAsync("humour script");
                    break;
                case MUSIC:
                    log.debug("Starting music simulation..");
                    simulationFuture = matlabEngine.evalAsync("music script");
                    break;
            }

            if (simulationFuture.isDone())
                log.debug("Simulation has been finished, disconnecting MATLAB..");
                matlabEngine.disconnectAsync();

        } catch (ExecutionException | InterruptedException | IOException e){
            log.error("Runtime error while starting a simulation", e);
        }
    }

    public void saveParameters(Simulation simulation){
        log.debug("Saving simulation parameters to file..");
        List<Float> simulationParameters = new ArrayList<>();
        simulationParameters.add(simulation.getUser().getStressEvent());
        simulationParameters.add(simulation.getUser().getStressLevel());
        simulationParameters.add(simulation.getUser().getPositiveBelief());
        simulationParameters.add(simulation.getUser().getNegativeBelief());

        String simulationParametersString = simulationParameters.stream()
                .map(i -> i.toString())
                .collect(Collectors.joining(","));

//        file with global settings for a simulation run
//        local file needed just to store parameters
        File simulationParametersFileGlobal = new File("params.csv");
        File simulationParametersFileLocal = new File(simulation.getFileName() + "_params.csv");

        try{
            simulationParametersFileGlobal.createNewFile();
            simulationParametersFileLocal.createNewFile();

            PrintWriter printWriter = new PrintWriter(simulationParametersFileGlobal);
            printWriter.write(simulationParametersString);

            printWriter = new PrintWriter(simulationParametersFileLocal);
            printWriter.write(simulationParametersString);
        } catch (IOException e){
            log.error("Error while saving simulation parameters", e);
        }
    }

//    TODO implement fetching stress level for the past 14 days
    public List<Float> getStressLevel(String userId){
        List<Float> stressLevel = new ArrayList<>();

        for (int i = 1; i < 15; i++)
            stressLevel.add((float)i);

        return stressLevel;
    }
}