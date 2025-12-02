package view;
import static com.coti.tools.Esdia.readInt;
import static com.coti.tools.Esdia.readString_ne;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import controller.Controller;
import model.BinaryRepository;
import model.Option;
import model.Question;

public class InteractiveView extends BaseView {


    @Override
    public void end() {
        
    }

    @Override
    public void init() {
        showMessage("BIENVENIDO A EXAMINATOR 3000");

        mostrarMenuPrincipal();
    }



    @Override
    public void showErrorMessage(String msg) {
        System.err.println(msg);
    }

    @Override
    public void showMessage(String msg) {
        System.out.println(msg);
    }

    private void mostrarMenuPrincipal() {
        boolean salir = false;
        while (!salir) {
            showMessage("\n--- MENU PRINCIPAL ---");
            showMessage("1) CRUD de Preguntas");
            showMessage("2) Exportación/Importación de Preguntas");
            showMessage("3) Creación de Preguntas Automáticas");
            showMessage("4) Modo Examen");
            showMessage("5) Salir");
            String opcion = readString_ne("Seleccione una opción: ");

            switch (opcion) {
                case "1":
                    showMessage("CRUD de Preguntas seleccionado.");
                    mostrarMenuCRUD();
                    break;
                default:
                    showErrorMessage("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private void mostrarMenuCRUD() {
        boolean salir = false;
        while (!salir) {
            showMessage("\n--- CRUD DE PREGUNTAS ---");
            showMessage("1) Crear nueva pregunta");
            showMessage("2) Listar preguntas existente");
            String opcion = readString_ne("Seleccione una opción: ");
            

            switch (opcion) {
                case "1":
                    showMessage("Crear nueva pregunta seleccionado.");
                    String author = readString_ne("Ingrese el autor de la pregunta: ");
                    String statement = readString_ne("Ingrese el enunciado de la pregunta: ");
                    int n = readInt("Ingrese el número de temas de la pregunta: ");
                    HashSet<String> topics = new HashSet<>();
                    for (int i = 0; i < n; i++) {
                        String topic = readString_ne("Ingrese el tema " + (i + 1) + ": ");
                        topics.add(topic);
                    }
                    String opcionA = readString_ne("Ingrese la opción A: ");
                    String opcionB = readString_ne("Ingrese la opción B: ");
                    String opcionC = readString_ne("Ingrese la opción C: ");
                    String opcionD = readString_ne("Ingrese la opción D: ");
                    String rationaleA = readString_ne("Ingrese la justificación para la opción A: ");
                    String rationaleB = readString_ne("Ingrese la justificación para la opción B: ");
                    String rationaleC = readString_ne("Ingrese la justificación para la opción C: ");
                    String rationaleD = readString_ne("Ingrese la justificación para la opción D: ");
                    String correctOption = readString_ne("Ingrese la opción correcta (A/B/C/D): ");
                    List<Option> options = controller.createOptions(opcionA, rationaleA, opcionB, rationaleB, opcionC, rationaleC, opcionD, rationaleD, correctOption);
                    // Aquí se debería llamar al controlador para crear la pregunta
                    controller.addQuestion(new Question(author, topics, statement, options));
                    showMessage("Pregunta creada exitosamente.");
                    break;
                case "2":
                    showMessage("Listar preguntas existente seleccionado.");
                    mostrarMenuListarPreguntas();
                    break;
                case "5":
                    salir = true;
                    break;
                default:
                    showErrorMessage("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private void mostrarMenuListarPreguntas() {
        boolean salir = false;
        while (!salir) {
            showMessage("\n--- LISTAR PREGUNTAS ---");
            showMessage("1) Listar todas las preguntas");
            String opcion = readString_ne("Seleccione una opción: ");

            switch (opcion) {
                case "1":
                    showMessage("Listar todas las preguntas seleccionado.");
                    listarPreguntasEnOrden();
                    break;
                default:
                    showErrorMessage("Opción no válida. Intente de nuevo.");
            }
        }

    }

    private void listarPreguntasEnOrden() {
        List<Question> preguntas = controller.getAllQuestions();
        if (preguntas.isEmpty()) {
            showMessage("No hay preguntas disponibles.");
            return;
        }
        ArrayList<Question> preguntasOrdenadas = new ArrayList<>(preguntas);
        preguntasOrdenadas.sort((q1, q2) -> q1.getStatement().compareTo(q2.getStatement()));
        for (Question q : preguntasOrdenadas) {
            mostrarPregunta(q);
        }
    }

    private void mostrarPregunta(Question q) {
        showMessage("\nID: " + q.getId());
        showMessage("Autor: " + q.getAuthor());
        showMessage("Fecha de Creación: " + q.getDate());
        showMessage("Enunciado: " + q.getStatement());
        showMessage("Opciones:");
        for (Option option : q.getOptions()) {
            showMessage("- " + option.getText() + (option.isCorrect() ? " (Correcta)" : ""));
        }
    }
        
}
