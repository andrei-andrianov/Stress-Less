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

//    TODO fix userInfo update with this function
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

        return userRepository.findById(id)
                .map(user -> {
                    user.setMindfulness(userPreferences.getMindfulness());
                    user.setHumour(userPreferences.getHumour());
                    user.setMusic(userPreferences.getMusic());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

//    Set simulation parameters for a user with {userId}
    @PutMapping("/simulationParameters/{userId}")
    public User putSimulationParameters(@RequestBody User userParameters, @PathVariable String userId) {

        return userRepository.findById(userId)
                .map(user -> {
                    user.setStressEvent(userParameters.getStressEvent());
                    user.setStressLevel(userParameters.getStressLevel());
                    user.setPositiveBelief(userParameters.getPositiveBelief());
                    user.setNegativeBelief(userParameters.getNegativeBelief());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}