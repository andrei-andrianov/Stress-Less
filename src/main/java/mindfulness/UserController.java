package mindfulness;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private final UserRepository userRepository;

    UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

//      Save new user info
    @PostMapping("/userInfo")
    public User postUserInfo(@Valid @RequestBody User newUser) {
        return userRepository.save(newUser);
    }

//      Get info for all users available
    @GetMapping("/userInfo")
    public List<User> getAllUserInfo() {
        return userRepository.findAll();
    }

//      Get the info for a user with id
    @GetMapping("/userInfo/{id}")
    public User getUserInfo(@PathVariable String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

//      Update the info of a user
//      This does not update user id
    @PutMapping("/userInfo/{id}")
    public User putUserInfo(@RequestBody User newUser, @PathVariable String id) {

        return userRepository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setAge(newUser.getAge());
                    user.setGender(newUser.getGender());
                    user.setOccupation(newUser.getOccupation());
                    user.setEthnicity(newUser.getEthnicity());
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return userRepository.save(newUser);
                });
    }
}