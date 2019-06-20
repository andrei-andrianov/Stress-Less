package mindfulness.controller;

import lombok.extern.slf4j.Slf4j;
import mindfulness.model.Simulation;
import mindfulness.repository.SimulationRepository;
import mindfulness.repository.UserRepository;
import mindfulness.service.SimulationService;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Map;

@Slf4j
@RestController
public class SimulationController {
    private final SimulationRepository simulationRepository;
    private final SimulationService simulationService;

    SimulationController(SimulationRepository simulationRepository, UserRepository userRepository){
        this.simulationRepository = simulationRepository;
        this.simulationService = new SimulationService(userRepository);
    }

//    Runs simulation for user with id, reads input initial values from file, saves results to file
    @PostMapping("/runSimulation/{userId}")
    public Simulation getRunSimulation(@RequestBody Map<String, Long> simulationParameters, @PathVariable String userId){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Simulation simulation = new Simulation(userId, timestamp, simulationService.suggestSimulation(userId),
                simulationService.generateFilename(userId, simulationService.suggestSimulation(userId), timestamp), simulationParameters);

        simulationService.runSimulation(simulation);

        return simulationRepository.save(simulation);
    }
}
