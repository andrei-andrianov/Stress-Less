package mindfulness.controller;

import lombok.extern.slf4j.Slf4j;
import mindfulness.exception.UserNotFoundException;
import mindfulness.model.Simulation;
import mindfulness.model.SimulationType;
import mindfulness.model.User;
import mindfulness.repository.SimulationRepository;
import mindfulness.repository.UserRepository;
import mindfulness.service.SimulationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@RestController
public class SimulationController {
    private final SimulationRepository simulationRepository;
    private final SimulationService simulationService;
    private final UserRepository userRepository;

    SimulationController(SimulationRepository simulationRepository, UserRepository userRepository){
        this.simulationRepository = simulationRepository;
        this.userRepository = userRepository;
        this.simulationService = new SimulationService(this.userRepository, this.simulationRepository);
    }

//    Runs simulation for user with {userId}
    @GetMapping("/runSimulation/{userId}")
    public Simulation getRunSimulation(@PathVariable String userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        Simulation simulation = new Simulation();
        simulation.setUser(user);
        simulation.setTimestamp(new Timestamp(System.currentTimeMillis()));
        simulation.setSimulationType(simulationService.suggestSimulation(userId));
        simulation.setFileName(simulationService.generateFilename(simulation));

        switch (simulation.getSimulationType()){
            case MUSIC:
                simulation.setDefParam1(0.5f);
                simulation.setDefParam2(0.5f);
                simulation.setDefParam3(0.5f);
                break;
            case HUMOUR:
                simulation.setDefParam1(0.9f);
                simulation.setDefParam2(0.008f);
                simulation.setDefParam3(0.005f);
                break;
            case MINDFULNESS:
                simulation.setDefParam1(0.015f);
                simulation.setDefParam2(0.9f);
                simulation.setDefParam3(0.015f);
                break;

                default:
                    break;
        }

        simulationService.runSimulation(simulation, false);

        return simulationRepository.save(simulation);
    }

    @GetMapping("/suggestSimulation/{userId}")
    public SimulationType getSuggestSimulation(@PathVariable String userId){
        return simulationService.suggestSimulation(userId);
    }

    @GetMapping("/stressLevel/{userId}")
    public List<Float> getStressLevel(@PathVariable String userId){
        return simulationService.getStressLevel(userId);
    }

}
