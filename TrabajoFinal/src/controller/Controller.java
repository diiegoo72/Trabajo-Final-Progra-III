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

    // Métodos
    public void start() throws RepositoryException {
        // Iniciar la vista de la aplicación
        view.init();
    }

    public void end() throws RepositoryException {
        model.saveQuestions();
        view.end();
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

    public void modifyQuestion(Question p) throws RepositoryException {
        model.modifyQuestion(p);
    }

    public void removeQuestion(Question p) throws RepositoryException {
        model.removeQuestion(p);
    }

    public void exportQuestions(String archivo) throws QuestionBackupIOException, RepositoryException {
        model.exportQuestions(archivo);
    }

    public void importQuestions(String archivoImport) throws QuestionBackupIOException, RepositoryException {
        model.importQuestions(archivoImport);
    }

    // -----------------------------
    // TEMAS Y PREGUNTAS
    // -----------------------------
    public HashSet<String> getAvailableTopics() throws RepositoryException {
        return model.getAvailableTopics();
    }

    public int getMaxQuestions(String tema) throws RepositoryException {
        return model.getMaxQuestions(tema);
    }

    // -----------------------------
    // INICIAR EXAMEN
    // -----------------------------
    public void iniciarExamen(String tema, int numPreguntas) {
        model.iniciarExamen(tema, numPreguntas);
    }

    // -----------------------------
    // OBTENER PREGUNTA ACTUAL
    // -----------------------------
    public Question getPregunta(int index) {
        return model.getPregunta(index);
    }

    // -----------------------------
    // RESPONDER PREGUNTA
    // -----------------------------
    public String responderPregunta(int index, String respuesta) {
        return model.responderPregunta(index, respuesta);
    }

    // -----------------------------
    // FINALIZAR EXAMEN
    // -----------------------------
    public void finalizarExamen() {
        model.finalizarExamen();
    }

    // -----------------------------
    // RESULTADOS
    // -----------------------------
    public Examen getExamen() {
        return model.getExamen();
    }

    public Question createQuestion(String tema, String modelo) throws QuestionCreatorException {
        return model.createQuestion(tema, modelo);
    }

    public ArrayList<String> getModelosDisponibles() {
        return model.getModelosDisponibles();
    }
}
