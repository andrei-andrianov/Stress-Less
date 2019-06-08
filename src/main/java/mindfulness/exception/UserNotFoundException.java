package mindfulness.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String id){
        super("Could not find user with the specified id: " + id);
    }
}
