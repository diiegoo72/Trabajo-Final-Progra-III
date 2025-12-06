package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Model {
    // Atributos
    private IRepository repository;
    private QuestionBackupIO backupHandler;
    private ArrayList<QuestionCreator> questionCreators;
    private ArrayList<Question> examQuestions;

    // falta constructor de questionCreators
    public Model(IRepository repository, QuestionBackupIO backupHandler) {
        this.repository = repository;
        this.backupHandler = backupHandler;
    }

    // Método para crear opciones de una pregunta
    public List<Option> createOptions(String opcionA, String rationaleA, String opcionB, String rationaleB,
            String opcionC, String rationaleC, String opcionD, String rationaleD, String correctOption) {
        List<Option> options = new ArrayList<>();
        options.add(new Option(opcionA, rationaleA, correctOption.equalsIgnoreCase("A")));
        options.add(new Option(opcionB, rationaleB, correctOption.equalsIgnoreCase("B")));
        options.add(new Option(opcionC, rationaleC, correctOption.equalsIgnoreCase("C")));
        options.add(new Option(opcionD, rationaleD, correctOption.equalsIgnoreCase("D")));
        return options;
    }

    // Método para agregar una pregunta a la colección
    public void addQuestion(Question question) throws RepositoryException {
        repository.addQuestion(question);
    }

    // Método para obtener todas las preguntas del repositorio
    public ArrayList<Question> getAllQuestions() throws RepositoryException {
        return repository.getAllQuestions();
    }

    public void saveQuestions() throws RepositoryException {
        repository.saveQuestions();
    }

    public void modifyQuestion(Question p) throws RepositoryException {
        repository.modifyQuestion(p);
    }

    public void removeQuestion(Question p) throws RepositoryException {
        repository.removeQuestion(p);
    }

    public void exportQuestions(String archivo) throws QuestionBackupIOException, RepositoryException {
        ArrayList<Question> preguntas = getAllQuestions();
        backupHandler.exportQuestions(preguntas, archivo);
    }

    public void importQuestions(String archivoImport) throws QuestionBackupIOException, RepositoryException {
        ArrayList<Question> preguntas = backupHandler.importQuestions(archivoImport);
        for (Question pregunta : preguntas) {
            repository.addQuestion(pregunta);
        }
    }

    public HashSet<String> getAvailableTopics() throws RepositoryException {
        HashSet<String> topics = new HashSet<>();
        examQuestions = getAllQuestions();

        for (Question q : examQuestions) {
            topics.addAll(q.getTopics());
        }

        return topics;
    }

    public int getMaxQuestions(String temaSeleccionado) throws RepositoryException {
        if (temaSeleccionado.equals("TODOS LOS TEMAS")) {
            return examQuestions.size();
        } else {
            int count = 0;
            for (Question q : examQuestions) {
                if (q.getTopics().contains(temaSeleccionado)) {
                    count++;
                }
            }
            return count;
        }
    }

    public ArrayList<Question> configExam(String temaSeleccionado, int numPreguntas) {
        ArrayList<Question> selectedQuestions = new ArrayList<>();
        ArrayList<Question> filteredQuestions = new ArrayList<>();

        if (temaSeleccionado.equals("TODOS LOS TEMAS")) {
            filteredQuestions.addAll(examQuestions);
        } else {
            for (Question q : examQuestions) {
                if (q.getTopics().contains(temaSeleccionado)) {
                    filteredQuestions.add(q);
                }
            }
        }

        // Seleccionar preguntas hasta alcanzar el número deseado
        for (int i = 0; i < numPreguntas && i < filteredQuestions.size(); i++) {
            selectedQuestions.add(filteredQuestions.get(i));
        }

        // Actualizar las preguntas del examen
        this.examQuestions = selectedQuestions;

        return selectedQuestions;
    }

}
