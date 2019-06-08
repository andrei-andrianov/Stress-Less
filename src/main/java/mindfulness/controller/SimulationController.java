package mindfulness.controller;

import lombok.extern.slf4j.Slf4j;
import mindfulness.model.Simulation;
import mindfulness.model.SimulationParameters;
import mindfulness.repository.SimulationRepository;
import mindfulness.service.SimulationService;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@Slf4j
public class SimulationController extends SimulationService{
    private final SimulationRepository simulationRepository;


    SimulationController(SimulationRepository simulationRepository){this.simulationRepository = simulationRepository;}


//    TODO: Check if a simulation type is attached to user otherwise call suggestSimulation
//    TODO: Filename generation
//    Runs simulation for user with id, reads input initial values from file, saves results to file
    @PostMapping("/runSimulation/{userId}")
    public Simulation getRunSimulation(@RequestBody SimulationParameters simulationParameters, @PathVariable String userId){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Simulation simulation = new Simulation(userId, timestamp, suggestSimulation(),
                generateFilename(userId, simulationParameters, timestamp), simulationParameters);

        runSimulation(simulation);

        return simulationRepository.save(simulation);
    }
}
