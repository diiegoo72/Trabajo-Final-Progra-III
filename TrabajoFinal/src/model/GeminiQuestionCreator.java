package model;

public class GeminiQuestionCreator implements QuestionCreator {
    
    // Atributos
    private String questionCreatorDescription;
    private String API_KEY;

    // Constructor
    public GeminiQuestionCreator(String aPI_KEY) {
        
    }

    @Override
    public Question createQuestion(String topic) {
        // Lógica para interactuar con la API de Gemini y crear una pregunta
        return null;
        
    }

    @Override
    public String getQuestionCreatorDescription() {
        return null;
    }
    

    // Métodos
    

    
}
