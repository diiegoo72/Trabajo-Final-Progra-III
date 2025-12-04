import controller.Controller;
import model.BinaryRepository;
import model.IRepository;
import model.JSONQuestionBackupIO;
import model.Model;
import model.QuestionBackupIO;
import view.BaseView;
import view.InteractiveView;


public class App {
    public static void main(String[] args) throws Exception {
        
        IRepository repository = new BinaryRepository();
        QuestionBackupIO backupHandler = new JSONQuestionBackupIO();
        BaseView view = new InteractiveView();
        Model model = new Model(repository, backupHandler);
        Controller controller = new Controller(model, view);

        controller.start();
    }
}
