package mindfulness;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    private final UserRepository userRepository;

    UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping("/userInfo")
    public User postUserInfo(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/userInfo")
    public User getUserInfo(@RequestParam(value="id", defaultValue="default@email.com") String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}