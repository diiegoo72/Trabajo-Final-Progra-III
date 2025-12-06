package model;

public class QuestionBackupIOException extends Exception {

    // Constructor por defecto
    public QuestionBackupIOException() {
        super();
    }
    // Constructor que acepta solo mensaje
    public QuestionBackupIOException(String message) {
        super(message);
    }

    // Constructor que acepta mensaje y causa
    public QuestionBackupIOException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor que acepta solo causa
    public QuestionBackupIOException(Throwable cause) {
        super(cause);
    }
}
