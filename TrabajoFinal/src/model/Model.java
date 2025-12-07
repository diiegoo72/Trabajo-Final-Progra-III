package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Model {
    // Atributos
    private IRepository repository;
    private QuestionBackupIO backupHandler;
    private ArrayList<QuestionCreator> questionCreators;
    private ArrayList<Question> allQuestions;
    private Examen examenActual;

    public Model(IRepository repository, QuestionBackupIO backupHandler, ArrayList<QuestionCreator> questionCreators) {
        this.repository = repository;
        this.backupHandler = backupHandler;
        this.questionCreators = new ArrayList<>();
        if (questionCreators != null) {
            this.questionCreators.addAll(questionCreators);
        }
    }

    // Método para crear opciones de una pregunta
    public List<Option> createOptions(String opcionA, String rationaleA, String opcionB, String rationaleB,
            String opcionC, String rationaleC, String opcionD, String rationaleD, String correctOption) {
        List<Option> options = new ArrayList<>();
        options.add(new Option(opcionA, rationaleA, correctOption.equalsIgnoreCase("A"), "A"));
        options.add(new Option(opcionB, rationaleB, correctOption.equalsIgnoreCase("B"), "B"));
        options.add(new Option(opcionC, rationaleC, correctOption.equalsIgnoreCase("C"), "C"));
        options.add(new Option(opcionD, rationaleD, correctOption.equalsIgnoreCase("D"), "D"));
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

    // Método para guardar las preguntas en el repositorio
    public void saveQuestions() throws RepositoryException {
        repository.saveQuestions();
    }

    // Método para modificar una pregunta en el repositorio
    public void modifyQuestion(Question p) throws RepositoryException {
        repository.modifyQuestion(p);
    }

    // Método para eliminar una pregunta del repositorio
    public void removeQuestion(Question p) throws RepositoryException {
        repository.removeQuestion(p);
    }

    // Método para exportar preguntas a un archivo
    public void exportQuestions(String archivo) throws QuestionBackupIOException, RepositoryException {
        ArrayList<Question> preguntas = getAllQuestions();
        backupHandler.exportQuestions(preguntas, archivo);
    }

    // Método para importar preguntas desde un archivo
    public void importQuestions(String archivoImport) throws QuestionBackupIOException, RepositoryException {
        ArrayList<Question> preguntas = backupHandler.importQuestions(archivoImport);
        for (Question pregunta : preguntas) {
            repository.addQuestion(pregunta);
        }
    }

    // Método para obtener los temas disponibles
    public HashSet<String> getAvailableTopics() throws RepositoryException {
        HashSet<String> topics = new HashSet<>();
        allQuestions = getAllQuestions();

        for (Question q : allQuestions) {
            topics.addAll(q.getTopics());
        }

        return topics;
    }

    // Método para obtener el número máximo de preguntas para un tema dado
    public int getMaxQuestions(String tema) throws RepositoryException {
        if (tema.equals("TODOS LOS TEMAS")) {
            return allQuestions.size();
        }

        int count = 0;
        for (Question q : allQuestions) {
            if (q.getTopics().contains(tema)) {
                count++;
            }
        }
        return count;
    }

    // Método para iniciar un examen
    public void iniciarExamen(String tema, int numPreguntas) {

        // Filtrar preguntas por tema
        List<Question> filtradas = new ArrayList<>();
        if (tema.equals("TODOS LOS TEMAS")) {
            filtradas.addAll(allQuestions);
        } else {
            for (Question q : allQuestions) {
                if (q.getTopics().contains(tema)) {
                    filtradas.add(q);
                }
            }
        }

        // Mezclar preguntas y seleccionar N primeras
        Collections.shuffle(filtradas);
        ArrayList<Question> seleccionadas = new ArrayList<>(filtradas.subList(0, numPreguntas));

        // Crear y guardar el examen actual
        examenActual = new Examen(seleccionadas, numPreguntas);
    }

    // Método para obtener una pregunta del examen actual
    public Question getPregunta(int index) {
        if (examenActual == null)
            return null;
        return examenActual.getPreguntas().get(index);
    }

    // Método para responder una pregunta del examen actual
    public String responderPregunta(int index, String respuesta) {
        // Códigos de colores ANSI
        final String RESET = "\u001B[0m";
        final String RED = "\u001B[31m";
        final String GREEN = "\u001B[32m";

        Question pregunta = examenActual.getPreguntas().get(index);

        // Si no contesta
        if (respuesta.isEmpty()) {
            examenActual.incrementarNoContestadas();
            return "\nNo has respondido esta pregunta.";
        }

        // Buscar opción seleccionada
        for (Option op : pregunta.getOptions()) {
            if (op.getUserAnswer().equalsIgnoreCase(respuesta)) {

                if (op.isCorrect()) {
                    examenActual.incrementarAcertadas();
                    return GREEN + "\n¡Respuesta correcta! (" + op.getRationale() + ")" + RESET;
                } else {
                    examenActual.incrementarIncorrectas();
                    return RED + "\nRespuesta incorrecta (" + op.getRationale() + ")" + RESET;
                }
            }
        }
        return "";
    }

    // Método para finalizar el examen actual
    public void finalizarExamen() {
        if (examenActual != null) {
            examenActual.finalizarExamen();
        }
    }

    // Método para obtener el examen actual
    public Examen getExamen() {
        return examenActual;
    }

    // Método para crear una pregunta usando un modelo específico de IA
    public Question createQuestion(String tema, String modelo) throws QuestionCreatorException {
        for (QuestionCreator qc : questionCreators) {
            if (qc.getQuestionCreatorDescription().contains(modelo)) {
                return qc.createQuestion(tema);
            }
        }
        throw new QuestionCreatorException("No se encontró un creador de preguntas para el modelo: " + modelo);

    }

    // Método para obtener los modelos de IA disponibles
    public ArrayList<String> getModelosDisponibles() {
        ArrayList<String> modelos = new ArrayList<>();
        for (QuestionCreator qc : questionCreators) {
            modelos.add(qc.getQuestionCreatorDescription());
        }
        return modelos;
    }

}
