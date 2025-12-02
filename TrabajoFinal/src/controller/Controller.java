package controller;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import model.Model;
import model.Option;
import model.Question;
import view.BaseView;

public class Controller {
    // Atributos
    private Model model;
    private BaseView view;

     // Constructor
    public Controller(Model model, BaseView view) {
        this.model = model;
        this.view = view;
        this.view.setController(this);
    }

    // Métodos
    public void start() {
        view.init();
    }

    public void end() {
        
    }



    // Método para crear opciones desde la vista
    public List<Option> createOptions(String opcionA, String rationaleA, String opcionB, String rationaleB, String opcionC,
            String rationaleC, String opcionD, String rationaleD, String correctOption) {
        return model.createOptions(opcionA, rationaleA, opcionB, rationaleB, opcionC, rationaleC, opcionD, rationaleD, correctOption);
    }

    // Método para agregar una pregunta desde la vista
    public void addQuestion(Question question) {
        model.addQuestion(question);
    }

    // Método para obtener todas las preguntas
    public List<Question> getAllQuestions() {
        return model.getAllQuestions();
    }
}
