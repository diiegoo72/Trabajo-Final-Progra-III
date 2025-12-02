import controller.Controller;
import model.BinaryRepository;
import model.IRepository;
import model.Model;
import view.BaseView;
import view.InteractiveView;


public class App {
    public static void main(String[] args) throws Exception {
        
        IRepository repository = new BinaryRepository();
        BaseView view = new InteractiveView();
        Model model = new Model(repository);
        Controller controller = new Controller(model, view);

        controller.start();
    }
}
