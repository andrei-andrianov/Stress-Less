package mindfulness.service;

import com.mathworks.engine.MatlabEngine;
import lombok.extern.slf4j.Slf4j;
import mindfulness.SimulationType;
import mindfulness.exception.UserNotFoundException;
import mindfulness.model.Simulation;
import mindfulness.model.User;
import mindfulness.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@Service
public class SimulationService {
    private static Future<MatlabEngine> matlabEngineFuture;

    private final UserRepository userRepository;

    public SimulationService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public SimulationType suggestSimulation(String userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        SimulationType simulationType = Collections.max(user.getPreferences().entrySet(), Map.Entry.comparingByValue()).getKey();
        return SimulationType.MINDFULNESS;
    }

    public String generateFilename(String userId, SimulationType simulationType, Timestamp timestamp){
        String filename = new StringBuilder()
                .append(userId)
                .append("-")
                .append(simulationType)
                .append("-")
                .append(timestamp)
                .toString();
        return filename;
    }

    // run matlab simulation asynchronously
    public void runSimulation(Simulation simulation) {
        // Declare an Engine future object and an Engine object.
        // Future object is used to return the result in case of an asynchronous call.
        matlabEngineFuture = MatlabEngine.startMatlabAsync();
        log.debug("MATLAB started asynchronously.");

        try {
            // Get the MATLAB engine object from the future.
            MatlabEngine matlabEngine = matlabEngineFuture.get();

            matlabEngine.fevalAsync(simulation.getFileName());
            matlabEngine.disconnectAsync();
        } catch (ExecutionException | InterruptedException e){
            log.error("Runtime error while running a simulation.", e);
        }
    }
}