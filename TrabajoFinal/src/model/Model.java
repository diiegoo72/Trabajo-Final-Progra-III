package model;

import java.util.ArrayList;
import java.util.List;

public class Model {
    // Atributos
    private IRepository repository;
    private QuestionBackupIO backupHandler;
    private ArrayList<QuestionCreator> questionCreators;

    // falta constructor
    public Model(IRepository repository) {
        this.repository = repository;
    }

    // Método para crear opciones
    public List<Option> createOptions(String opcionA, String rationaleA, String opcionB, String rationaleB,
            String opcionC, String rationaleC, String opcionD, String rationaleD, String correctOption) {
        List<Option> options = new ArrayList<>();
        options.add(new Option(opcionA, rationaleA, correctOption.equalsIgnoreCase("A")));
        options.add(new Option(opcionB, rationaleB, correctOption.equalsIgnoreCase("B")));
        options.add(new Option(opcionC, rationaleC, correctOption.equalsIgnoreCase("C")));
        options.add(new Option(opcionD, rationaleD, correctOption.equalsIgnoreCase("D")));
        return options;
    }

    // Método para agregar una pregunta al repositorio
    public void addQuestion(Question question) {
        repository.addQuestion(question);
    }

    // Método para obtener todas las preguntas del repositorio
    public List<Question> getAllQuestions() {
        return repository.getAllQuestions();
    }


}
