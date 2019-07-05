package mindfulness.controller;

import mindfulness.model.User;
import mindfulness.exception.UserNotFoundException;
import mindfulness.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private final UserRepository userRepository;

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    TODO Fix userInfo update with this function
//    Save new user info
    @PostMapping("/userInfo")
    public User postUserInfo(@Valid @RequestBody User newUser) {
        return userRepository.save(newUser);
    }

//    Get info for all users available
    @GetMapping("/userInfo")
    public List<User> getAllUserInfo() {
        return userRepository.findAll();
    }

//    Get the info for a user with {id}
    @GetMapping("/userInfo/{id}")
    public User getUserInfo(@PathVariable String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

//    Update the info of a user
//    This does not update user {id}
//    TODO Check if this sets not specified variables to null
    @PutMapping("/userInfo/{id}")
    public User putUserInfo(@RequestBody User newUser, @PathVariable String id) {

        return userRepository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setAge(newUser.getAge());
                    user.setGender(newUser.getGender());
                    user.setOccupation(newUser.getOccupation());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

//    Set simulation preferences for a user with {id}
    @PutMapping("/userPreferences/{id}")
    public User putUserPreferences(@RequestBody User userPreferences, @PathVariable String id) {

//        If a user doesnt have some preference set it to 0 instead of null
        return userRepository.findById(id)
                .map(user -> {
                    user.setMindfulness(
                            userPreferences.getMindfulness() != null ? userPreferences.getMindfulness() : 0);
                    user.setHumour(userPreferences.getHumour() != null ? userPreferences.getHumour() : 0);
                    user.setMusic(userPreferences.getMusic() != null ? userPreferences.getMusic() : 0);
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

//    Set simulation parameters for a user with {userId}
    @PutMapping("/simulationParameters/{userId}")
    public User putSimulationParameters(@RequestBody User userParameters, @PathVariable String userId) {

//        If a user doesnt have some parameter set it to 0 instead of null
        return userRepository.findById(userId)
                .map(user -> {
                    user.setStressEvent(userParameters.getStressEvent() != null ? userParameters.getStressEvent() : 0);
                    user.setStressLevel(userParameters.getStressLevel() != null ? userParameters.getStressLevel() : 0);
                    user.setPositiveBelief(
                            userParameters.getPositiveBelief() != null ? userParameters.getPositiveBelief() : 0);
                    user.setNegativeBelief(
                            userParameters.getNegativeBelief() != null ? userParameters.getNegativeBelief() : 0);
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}