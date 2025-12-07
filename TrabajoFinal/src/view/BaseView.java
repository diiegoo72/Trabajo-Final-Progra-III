package view;

import controller.Controller;
import model.Question;
import view.InteractiveView.ModoPregunta;


public abstract class BaseView {
    // Atributos comunes
    protected Controller controller;

    // Métodos abstractos para implementar en las vistas concretas
    public abstract void init();

    protected abstract void showMessage(String msg);

    protected abstract void showErrorMessage(String msg);

    protected abstract void showGoodMessage(String string);
    
    protected abstract void end();

    protected abstract void mostrarPregunta(Question q, int index, ModoPregunta modo);

    protected abstract void listarPreguntasPorTema();

    protected abstract void listarPreguntasEnOrdenDeFecha();

    // Setter del controlador
    public void setController(Controller controller) {
        this.controller = controller;
    }


}
