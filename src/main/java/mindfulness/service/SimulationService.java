package mindfulness.service;

import com.mathworks.engine.MatlabEngine;
import lombok.extern.slf4j.Slf4j;
import mindfulness.SimulationType;
import mindfulness.model.Simulation;
import mindfulness.model.SimulationParameters;

import java.sql.Timestamp;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
public abstract class SimulationService {
    private static Future<MatlabEngine> matlabEngineFuture;

    // TODO: implement simulation suggestion based on user preferences
    public SimulationType suggestSimulation(){
        return SimulationType.YOGA;
    }

    public String generateFilename(String userId, SimulationParameters simulationParameters, Timestamp timestamp){
        String filename = new StringBuilder()
                .append(userId)
                .append("-")
                .append(simulationParameters.getParameters().get("type"))
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

            //TODO: Call function in MATLAB asynchronously.
            matlabEngine.fevalAsync(simulation.getFileName());
            matlabEngine.disconnectAsync();
        } catch (ExecutionException | InterruptedException e){
            log.error("Runtime error while running a simulation.", e);
        }
    }
}