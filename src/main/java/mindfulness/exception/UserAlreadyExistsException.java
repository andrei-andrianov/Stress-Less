package mindfulness.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String id){
        super("User with the specified id already exists in the system: " + id);
    }
}
