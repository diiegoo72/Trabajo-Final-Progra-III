package view;
import controller.Controller;

public abstract class BaseView {
    // Atributos comunes
    protected Controller controller;

    // Métodos abstractos para implementar en las vistas concretas
    public abstract void init();

    public abstract void showMessage(String msg);

    public abstract void showErrorMessage(String msg);

    public abstract void end();

    // Setter para el controlador
    public void setController(Controller controller) {
        this.controller = controller;
    }

}
