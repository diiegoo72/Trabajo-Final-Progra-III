package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import model.Examen;
import model.Model;
import model.Option;
import model.Question;
import model.RepositoryException;
import model.QuestionBackupIOException;
import model.QuestionCreatorException;
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

    // Método para iniciar la aplicación
    public void start() throws RepositoryException {
        view.init();
    }

    // Método para finalizar la aplicación
    public void end() throws RepositoryException {
        model.saveQuestions();
    }

    // Método para crear opciones desde la vista
    public List<Option> createOptions(String opcionA, String rationaleA, String opcionB, String rationaleB,String opcionC,String rationaleC, String opcionD, String rationaleD, String correctOption) {
        return model.createOptions(opcionA, rationaleA, opcionB, rationaleB, opcionC, rationaleC, opcionD, rationaleD,
                correctOption);
    }

    // Método para agregar una pregunta desde la vista
    public void addQuestion(Question question) throws RepositoryException {
        model.addQuestion(question);
    }

    // Método para obtener todas las preguntas
    public ArrayList<Question> getAllQuestions() throws RepositoryException {
        return model.getAllQuestions();
    }

    // Método para modificar una pregunta
    public void modifyQuestion(Question p) throws RepositoryException {
        model.modifyQuestion(p);
    }

    // Método para eliminar una pregunta
    public void removeQuestion(Question p) throws RepositoryException {
        model.removeQuestion(p);
    }

    // Método para exportar preguntas
    public void exportQuestions(String archivo) throws QuestionBackupIOException, RepositoryException {
        model.exportQuestions(archivo);
    }

    // Método para importar preguntas
    public void importQuestions(String archivoImport) throws QuestionBackupIOException, RepositoryException {
        model.importQuestions(archivoImport);
    }

    // Método para obtener los temas disponibles
    public HashSet<String> getAvailableTopics() throws RepositoryException {
        return model.getAvailableTopics();
    }

    // Método para obtener el número máximo de preguntas de un tema
    public int getMaxQuestions(String tema) throws RepositoryException {
        return model.getMaxQuestions(tema);
    }

    // Método para iniciar un examen
    public void iniciarExamen(String tema, int numPreguntas) {
        model.iniciarExamen(tema, numPreguntas);
    }

    // Método para obtener una pregunta del examen
    public Question getPregunta(int index) {
        return model.getPregunta(index);
    }

    // Método para dar feedback a de una respuesta a una pregunta del examen
    public String responderPregunta(int index, String respuesta) {
        return model.responderPregunta(index, respuesta);
    }

    // Método para finalizar un examen
    public void finalizarExamen() {
        model.finalizarExamen();
    }

    // Método para obtener el examen actual
    public Examen getExamen() {
        return model.getExamen();
    }

    // Método para crear una pregunta nueva con IA
    public Question createQuestion(String tema, String modelo) throws QuestionCreatorException {
        return model.createQuestion(tema, modelo);
    }

    // Método para obtener los modelos disponibles de IA
    public ArrayList<String> getModelosDisponibles() {
        return model.getModelosDisponibles();
    }
}
