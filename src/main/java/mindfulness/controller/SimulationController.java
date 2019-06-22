package mindfulness.controller;

import lombok.extern.slf4j.Slf4j;
import mindfulness.exception.UserNotFoundException;
import mindfulness.model.Simulation;
import mindfulness.model.User;
import mindfulness.repository.SimulationRepository;
import mindfulness.repository.UserRepository;
import mindfulness.service.SimulationService;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@Slf4j
@RestController
public class SimulationController {
    private final SimulationRepository simulationRepository;
    private final SimulationService simulationService;
    private final UserRepository userRepository;

    SimulationController(SimulationRepository simulationRepository, UserRepository userRepository){
        this.simulationRepository = simulationRepository;
        this.userRepository = userRepository;
        this.simulationService = new SimulationService(this.userRepository);
    }

//    Runs simulation for user with {id}
    @GetMapping("/runSimulation/{userId}")
    public Simulation getRunSimulation(@PathVariable String userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        Simulation simulation = new Simulation();
        simulation.setUser(user);
        simulation.setTimestamp(new Timestamp(System.currentTimeMillis()));
        simulation.setSimulationType(simulationService.suggestSimulation(userId));
        simulation.setFileName(simulationService.generateFilename(simulation));

        simulationService.runSimulation(simulation);

        return simulationRepository.save(simulation);
    }

//    Set simulation preferences for a user with {id}
    @PutMapping("/simulationParameters/{id}")
    public User putSimulationParameters(@PathVariable String userId) {
        User newUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        return userRepository.findById(userId)
                .map(user -> {
                    user.setMindfulness(newUser.getMindfulness());
                    user.setHumour(newUser.getHumour());
                    user.setMusic(newUser.getMusic());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(userId));
    }}
