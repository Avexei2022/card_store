package group5984.model.exceptoins;

/**
 * Счет отсутствует.
 */
public class ResourceNotFoundException  extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
