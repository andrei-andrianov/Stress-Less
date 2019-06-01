package mindfulness;

import com.mathworks.engine.MatlabEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@Slf4j
public class SimulationController {
    private final SimulationRepository simulationRepository;
    private static Future<MatlabEngine> matlabEngineFuture;


    SimulationController(SimulationRepository simulationRepository){this.simulationRepository = simulationRepository;}

    // TODO: implement simulation suggestion based on user preferences
    private SimulationType suggestSimulation(){
        return SimulationType.YOGA;
    }

    public void runSimulation(Simulation simulation) {
        // Declare an Engine future object and an Engine object.
        // Future object is used to return the result in case of an asynchronous call.
        matlabEngineFuture = MatlabEngine.startMatlabAsync();
        log.debug("MATLAB started asynchronously.");

        try {
            // Get the MATLAB engine object from the future.
            MatlabEngine matlabEngine = matlabEngineFuture.get();

            //TODO: Call function in MATLAB asynchronously.
            // Check if the input for the simulation is correct?
            matlabEngine.fevalAsync(simulation.getFileName());
            matlabEngine.disconnectAsync();
        } catch (ExecutionException | InterruptedException e){
            log.error("Runtime error.", e);
        }
    }

//    TODO: Check if a simulation type is attached to user otherwise call suggestSimulation
//    TODO: Filename generation
//    Runs simulation for user with id, reads input initial values from file, saves results to file
    @GetMapping("/runSimulation/{userId}")
    public Simulation getRunSimulation(@PathVariable String userId){
        Simulation simulation = new Simulation(
                userId, new Timestamp(System.currentTimeMillis()), suggestSimulation(), "simulation_yoga.m");

        runSimulation(simulation);

        return simulationRepository.save(simulation);
    }
}
