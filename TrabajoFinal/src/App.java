import java.util.ArrayList;

import controller.Controller;
import model.BinaryRepository;
import model.IRepository;
import model.JSONQuestionBackupIO;
import model.Model;
import model.QuestionBackupIO;
import model.QuestionCreator;
import model.GeminiQuestionCreator;
import view.BaseView;
import view.InteractiveView;

public class App {
    public static void main(String[] args) throws Exception {
        // Inicialización del repositorio (binario en este caso)
        IRepository repository = new BinaryRepository();
        // Inicialización del manejador de backup (JSON en este caso)
        QuestionBackupIO backupHandler = new JSONQuestionBackupIO();
        // Lista de creadores de preguntas basados en IA
        ArrayList<QuestionCreator> questionCreators = new ArrayList<>();

        // Comprobamos si se pasó el parámetro para crear un QuestionCreator
        if (args.length == 3 && args[0].equalsIgnoreCase("-question-creator")) {
            String modelo = args[1];
            String apiKey = args[2];

            // Por ahora solo manejamos Gemini, se puede ampliar a otros modelos
            if (modelo.toLowerCase().startsWith("gemini")) {
                questionCreators.add(new GeminiQuestionCreator(apiKey, modelo));
                System.out.println("GeminiQuestionCreator añadido correctamente.");
            }
        }

        BaseView view = new InteractiveView();
        Model model = new Model(repository, backupHandler, questionCreators);
        Controller controller = new Controller(model, view);

        controller.start();
    }
}
