package mindfulness;

public class UserNotFoundException extends RuntimeException {

    UserNotFoundException(String id){
        super("Could not find user with the specified id: " + id);
    }
}
