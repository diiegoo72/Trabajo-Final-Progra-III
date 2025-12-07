package model;

public class QuestionCreatorException extends Exception {

    // Constructor
    public QuestionCreatorException() {
        super();
    }

    // Constructor con mensaje
    public QuestionCreatorException(String message) {
        super(message);
    }

    // Constructor con mensaje y causa
    public QuestionCreatorException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor con causa
    public QuestionCreatorException(Throwable cause) {
        super(cause);
    }
}
