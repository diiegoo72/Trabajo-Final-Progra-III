package controller;

import java.util.ArrayList;
import java.util.List;

import model.Model;
import model.Option;
import model.Question;
import model.RepositoryException;
import model.QuestionBackupIOException;
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
    public void start() throws RepositoryException {
        StringBuilder banner = new StringBuilder();
        banner.append("\n");
        banner.append("███████╗██╗  ██╗ █████╗ ███╗   ███╗██╗███╗   ██╗ █████╗ ████████╗ ██████╗ ██████╗     ██████╗  ██████╗  ██████╗  ██████╗ \r\n" + //
                        "██╔════╝╚██╗██╔╝██╔══██╗████╗ ████║██║████╗  ██║██╔══██╗╚══██╔══╝██╔═══██╗██╔══██╗    ╚════██╗██╔═████╗██╔═████╗██╔═████╗\r\n" + //
                        "█████╗   ╚███╔╝ ███████║██╔████╔██║██║██╔██╗ ██║███████║   ██║   ██║   ██║██████╔╝     █████╔╝██║██╔██║██║██╔██║██║██╔██║\r\n" + //
                        "██╔══╝   ██╔██╗ ██╔══██║██║╚██╔╝██║██║██║╚██╗██║██╔══██║   ██║   ██║   ██║██╔══██╗     ╚═══██╗████╔╝██║████╔╝██║████╔╝██║\r\n" + //
                        "███████╗██╔╝ ██╗██║  ██║██║ ╚═╝ ██║██║██║ ╚████║██║  ██║   ██║   ╚██████╔╝██║  ██║    ██████╔╝╚██████╔╝╚██████╔╝╚██████╔╝\r\n" + //
                        "╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝    ╚═════╝  ╚═════╝  ╚═════╝  ╚═════╝ \r\n" + //
                        "                                                                                                                         ");
        banner.append("\n");
        view.showMessage(banner.toString());

        // Cargar las preguntas desde el repositorio al iniciar la aplicación
        ArrayList<Question> questions = model.getAllQuestions();

        // Mensaje claro sobre el estado del repositorio
        if (questions != null && !questions.isEmpty()) {
            view.showMessage("Se han cargado " + questions.size() + " preguntas desde el repositorio.");
        } else {
            view.showMessage("No hay preguntas en el repositorio binario del home del usuario.");
        }

        // Iniciar la vista de la aplicación
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
    public void addQuestion(Question question) throws RepositoryException {
        model.addQuestion(question);
    }

    // Método para obtener todas las preguntas
    public ArrayList<Question> getAllQuestions() throws RepositoryException {
        return model.getAllQuestions();
    }

    public void modifyQuestion(Question p) throws RepositoryException {
        model.modifyQuestion(p);
    }

    public void removeQuestion(Question p) throws RepositoryException {
        model.removeQuestion(p);
    }

    public void exportQuestions(String archivo) throws QuestionBackupIOException, RepositoryException {
        model.exportQuestions(archivo);
    }
}
