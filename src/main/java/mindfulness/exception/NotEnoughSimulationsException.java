package mindfulness.exception;

public class NotEnoughSimulationsException extends RuntimeException {
    public NotEnoughSimulationsException(String id, Integer n, Integer min){
        super("Not enough simulations (" + n + ") for a user with id: " + id + ". Min number of simulations is " + min);
    }
}
