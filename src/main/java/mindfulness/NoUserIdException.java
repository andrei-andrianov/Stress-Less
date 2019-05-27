package mindfulness;

public class NoUserIdException extends RuntimeException{

    NoUserIdException(){
        super("Cannot save data without specified user id. User email is mandatory for identification.");
    }
}
