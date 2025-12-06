package model;

public class RepositoryException extends Exception {

    // Constructor por defecto
    public RepositoryException() {
        super();
    }

    // Constructor que acepta solo mensaje
    public RepositoryException(String message) {
        super(message);
    }

    // Constructor que acepta mensaje y causa
    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor que acepta solo la causa
    public RepositoryException(Throwable cause) {
        super(cause);
    }
    
}
