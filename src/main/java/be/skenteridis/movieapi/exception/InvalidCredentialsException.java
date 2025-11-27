package be.skenteridis.movieapi.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Invalid provided credentials!");
    }
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
