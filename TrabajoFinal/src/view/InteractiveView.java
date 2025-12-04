package view;

import static com.coti.tools.Esdia.readInt;
import static com.coti.tools.Esdia.readString_ne;
import static com.coti.tools.Esdia.readString;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import model.Option;
import model.Question;
import model.QuestionBackupIOException;
import model.RepositoryException;

public class InteractiveView extends BaseView {

    @Override
    public void end() {

    }

    // Método para iniciar la vista interactiva
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

    // -------------------------
    // --------- MENÚS ---------
    // -------------------------
    private void mostrarMenuPrincipal() {
        boolean salir = false;
        while (!salir) {
            vaciarPantalla();
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
                case "2":
                    showMessage("Exportación/Importación de Preguntas seleccionado.");
                    mostrarMenuImpExp();
                    waitForUserInput();
                    break;
                default:
                    showErrorMessage("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private void mostrarMenuImpExp() {
        boolean salir = false;
        while (!salir) {
            vaciarPantalla();
            showMessage("\n--- EXPORTACIÓN/IMPORTACIÓN DE PREGUNTAS ---");
            showMessage("1) Exportar preguntas a un JSON");
            showMessage("2) Importar preguntas desde un JSON");
            showMessage("3) Volver al menú principal");
            String opcion = readString_ne("Seleccione una opción: ");

            switch (opcion) {
                case "1":
                    showMessage("Exportar preguntas a un JSON seleccionado.");
                    String archivo = readString_ne("Ingrese el nombre del archivo de destino (con .json): ");
                    try {
                        controller.exportQuestions(archivo);
                    } catch (QuestionBackupIOException e) {
                        e.printStackTrace();
                    } catch (RepositoryException e) {
                        e.printStackTrace();
                    }
                    waitForUserInput();
                    break;
                case "2":
                    showMessage("Importar preguntas seleccionado.");
                    // Lógica para importar preguntas
                    waitForUserInput();
                    break;
                case "3":
                    salir = true;
                    break;
                default:
                    showErrorMessage("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private void mostrarMenuCRUD() {
        boolean salir = false;
        while (!salir) {
            // vaciar la pantalla
            vaciarPantalla();
            showMessage("\n--- CRUD DE PREGUNTAS ---");
            showMessage("1) Crear nueva pregunta");
            showMessage("2) Listar preguntas existentes");
            showMessage("3) Ver detalles de una pregunta");
            showMessage("4) Volver al menú principal");
            String opcion = readString_ne("Seleccione una opción: ");

            switch (opcion) {
                case "1":
                    showMessage("Crear nueva pregunta seleccionado.");
                    crearNuevaPregunta();
                    break;
                case "2":
                    showMessage("Listar preguntas existente seleccionado.");
                    mostrarMenuListarPreguntas();
                    break;
                case "3":
                    showMessage("Ver detalles de una pregunta seleccionado.");
                    listarPreguntasEnOrdenDeFecha();
                    mostrarDetallesPregunta();
                    break;
                case "4":
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
            vaciarPantalla();
            showMessage("\n--- LISTAR PREGUNTAS ---");
            showMessage("1) Listar todas las preguntas por orden de fecha de creación");
            showMessage("2) Listar preguntas por tema");
            showMessage("3) Volver al menú anterior");
            String opcion = readString_ne("Seleccione una opción: ");

            switch (opcion) {
                case "1":
                    showMessage("Listar todas las preguntas por orden de fecha de creación seleccionado.");
                    listarPreguntasEnOrdenDeFecha();
                    waitForUserInput();
                    break;
                case "2":
                    showMessage("Listar preguntas por tema seleccionado.");
                    listarPreguntasPorTema();
                    waitForUserInput();
                    break;
                case "3":
                    salir = true;
                    break;
                default:
                    showErrorMessage("Opción no válida. Intente de nuevo.");
            }
        }

    }

    private void mostrarDetallesPregunta() {
        int index = readInt("Ingrese el número de la pregunta para ver sus detalles: ");
        try {
            List<Question> preguntas = controller.getAllQuestions();
            if (index < 1 || index > preguntas.size()) {
                showErrorMessage("Número de pregunta inválido.");
                waitForUserInput();
            } else {
                vaciarPantalla();
                mostrarPregunta(preguntas.get(index - 1), index);
                mostrarMenuDetallesPregunta(preguntas.get(index - 1));
            }
        } catch (RepositoryException e) {
            showErrorMessage(e.getMessage());
        }
    }

    private void mostrarMenuDetallesPregunta(Question p) {
        boolean salir = false;
        while (!salir) {
            showMessage("\n--- DETALLES DE PREGUNTA ---");
            showMessage("1) Modificar algún atributo de la pregunta");
            showMessage("2) Eliminar la pregunta");
            showMessage("3) Volver al menú anterior");
            String opcion = readString_ne("Seleccione una opción: ");

            switch (opcion) {
                case "1":
                    showMessage("Modificar algún atributo de la pregunta seleccionado.");
                    // Lógica para modificar la pregunta
                    mostrarMenuModificarPregunta(p);
                    waitForUserInput();
                    break;
                case "2":
                    showMessage("Eliminar la pregunta seleccionado.");
                    // Lógica para eliminar la pregunta
                    try {
                        controller.removeQuestion(p);
                        showMessage("Pregunta eliminada exitosamente.");
                        salir = true;
                    } catch (RepositoryException e) {
                        e.printStackTrace();
                        showErrorMessage(e.getMessage());
                    }
                    waitForUserInput();
                    break;
                case "3":
                    salir = true;
                    break;
                default:
                    showErrorMessage("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private void mostrarMenuModificarPregunta(Question p) {
        boolean salir = false;
        while (!salir) {
            showMessage("\n--- MODIFICAR PREGUNTA ---");
            showMessage("1) Modificar autor"); 
            showMessage("2) Modificar enunciado");
            showMessage("3) Modificar temas");
            showMessage("4) Modificar opciones");
            showMessage("5) Volver al menú anterior");

            String opcion = readString_ne("Seleccione una opción: ");

            switch (opcion) {
                case "1":
                    showMessage("Modificar autor seleccionado.");
                    String nuevoAutor = readString_ne("Ingrese el nuevo autor: ");
                    p.setAuthor(nuevoAutor); //MIRAR Q ESTA MAL, hay q hacer funcion en model, o en 
                    showMessage("¡Autor modificado correctamente!");
                    waitForUserInput();
                    break;
                case "2":
                    showMessage("Modificar enunciado seleccionado.");
                    String nuevoEnunciado = readString_ne("Ingrese el nuevo enunciado: ");
                    p.setStatement(nuevoEnunciado);
                    showMessage("¡Enunciado modificado correctamente!");
                    waitForUserInput();
                    break;
                case "3":
                    showMessage("Modificar temas seleccionado.");
                    int n = readInt("Ingrese el número de temas de la pregunta: ");
                    HashSet<String> nuevosTemas = new HashSet<>();
                    for (int i = 0; i < n; i++) {
                        String tema = readString_ne("Ingrese el tema " + (i + 1) + ": ");
                        tema = tema.toUpperCase();
                        nuevosTemas.add(tema);
                    }
                    p.setTopics(nuevosTemas);
                    showMessage("¡Temas modificados correctamente!");
                    waitForUserInput();
                    break;
                case "4":
                    showMessage("Modificar opciones seleccionado.");
                    String opcionA = readString_ne("Ingrese la opción A: ");
                    String opcionB = readString_ne("Ingrese la opción B: ");
                    String opcionC = readString_ne("Ingrese la opción C: ");
                    String opcionD = readString_ne("Ingrese la opción D: ");
                    String rationaleA = readString_ne("Ingrese la justificación para la opción A: ");
                    String rationaleB = readString_ne("Ingrese la justificación para la opción B: ");
                    String rationaleC = readString_ne("Ingrese la justificación para la opción C: ");
                    String rationaleD = readString_ne("Ingrese la justificación para la opción D: ");
                    String correctOption;
                    do {
                        correctOption = readString_ne("Ingrese la opción correcta (A/B/C/D): ");
                    } while (!correctOption.equalsIgnoreCase("A")
                            && !correctOption.equalsIgnoreCase("B")
                            && !correctOption.equalsIgnoreCase("C")
                            && !correctOption.equalsIgnoreCase("D"));

                    // Crear las opciones
                    List<Option> options = controller.createOptions(opcionA, rationaleA, opcionB, rationaleB, opcionC,
                            rationaleC, opcionD, rationaleD, correctOption);
                    p.setOptions(options);
                    showMessage("¡Opciones modificadas correctamente!");
                    waitForUserInput();
                    break;
                case "5":
                    salir = true;
                    try {
                        controller.modifyQuestion(p);
                    } catch (RepositoryException e) {
                        e.printStackTrace();
                        showErrorMessage(e.getMessage());
                    }
                    break;
                default:
                    showErrorMessage("Opción no válida. Intente de nuevo.");
            }

        }
    }

    private void vaciarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void waitForUserInput() {
        showMessage("");
        showMessage("Pulsa enter para continuar...");
        readString("");
    }

    private void crearNuevaPregunta() {
        String author = readString_ne("Ingrese el autor de la pregunta: ");
        String statement = readString_ne("Ingrese el enunciado de la pregunta: ");
        int n = readInt("Ingrese el número de temas de la pregunta: ");
        HashSet<String> topics = new HashSet<>();
        for (int i = 0; i < n; i++) {
            String topic = readString_ne("Ingrese el tema " + (i + 1) + ": ");
            topic = topic.toUpperCase();
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
        String correctOption;
        do {
            correctOption = readString_ne("Ingrese la opción correcta (A/B/C/D): ");
        } while (!correctOption.equalsIgnoreCase("A")
                && !correctOption.equalsIgnoreCase("B")
                && !correctOption.equalsIgnoreCase("C")
                && !correctOption.equalsIgnoreCase("D"));

        // Crear las opciones
        List<Option> options = controller.createOptions(opcionA, rationaleA, opcionB, rationaleB, opcionC, rationaleC,
                opcionD, rationaleD, correctOption);

        // Crear la pregunta y agregarla al repositorio
        try {
            controller.addQuestion(new Question(author, topics, statement, options));
            showMessage("Pregunta creada exitosamente.");
        } catch (RepositoryException e) {
            showErrorMessage(e.getMessage());
        }
    }

    private void listarPreguntasPorTema() {
        String tema = readString_ne("Ingrese el tema por el cual filtrar las preguntas: ");
        tema = tema.toUpperCase();
        try {
            List<Question> preguntas = controller.getAllQuestions();
            boolean encontrado = false;
            for (int i = 0; i < preguntas.size(); i++) {
                Question q = preguntas.get(i);
                if (q.getTopics().contains(tema)) {
                    mostrarPregunta(q, i + 1);
                    encontrado = true;
                }
            }
            if (!encontrado) {
                showMessage("No se encontraron preguntas para el tema especificado.");
            }
        } catch (RepositoryException e) {
            showErrorMessage(e.getMessage());
        }
    }

    private void listarPreguntasEnOrdenDeFecha() {
        try {
            List<Question> preguntas = controller.getAllQuestions();
            if (preguntas.isEmpty()) {
                showMessage("No hay preguntas disponibles.");
                return;
            }
            // Mostrar en el orden que tienen en el ArrayList (sin reordenar)
            for (int i = 0; i < preguntas.size(); i++) {
                mostrarPregunta(preguntas.get(i), i + 1);
            }
        } catch (RepositoryException e) {
            showErrorMessage(e.getMessage());
        }
    }

    private void mostrarPregunta(Question q, int index) {
        showMessage("\nPregunta " + index);
        showMessage("Autor: " + q.getAuthor());
        showMessage("Temas: " + String.join(", ", q.getTopics()));
        showMessage("Enunciado: " + q.getStatement());
        showMessage("Opciones:");
        List<Option> opts = q.getOptions();
        for (int i = 0; i < opts.size(); i++) {
            Option option = opts.get(i);
            char letter = (char) ('A' + i);
            showMessage((letter + ") " + option.getText() + (option.isCorrect() ? " (Correcta)" : "")));
        }
    }

}
