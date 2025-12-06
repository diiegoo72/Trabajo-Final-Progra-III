package view;

import static com.coti.tools.Esdia.readInt;
import static com.coti.tools.Esdia.readString_ne;
import static com.coti.tools.Esdia.readString;

import java.lang.reflect.Array;
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
        showMessage("\nGracias por usar la aplicación. ¡Hasta luego!");

    }

    // Método para iniciar la vista interactiva
    @Override
    public void init() {
        bannerInicio();

        // Cargar las preguntas desde el repositorio al iniciar la aplicación
        ArrayList<Question> questions = new ArrayList<>();
        try {
            questions = controller.getAllQuestions();
        } catch (RepositoryException e) {
            e.printStackTrace();
            showErrorMessage("Error al cargar las preguntas del repositorio: " + e.getMessage());
        }

        // Mensaje claro sobre el estado del repositorio
        if (questions != null && !questions.isEmpty()) {
            showMessage("Se han cargado " + questions.size() + " preguntas desde el repositorio.");
        } else {
            showMessage("No hay preguntas en el repositorio binario del home del usuario.");
        }
        waitForUserInput();
        // Mostrar el menú principal
        mostrarMenuPrincipal();
        // Finalizar la vista al salir
        end();
    }

    @Override
    public void showErrorMessage(String msg) {
        final String RED = "\u001B[31m"; // Código ANSI para rojo
        final String RESET = "\u001B[0m"; // Resetea al color normal
        System.err.println(RED + "\n" + msg + RESET);
    }

    @Override
    public void showMessage(String msg) {
        System.out.println(msg);
    }

    private void showExitoMessage(String string) {
        final String GREEN = "\u001B[32m"; // Código ANSI para verde
        final String RESET = "\u001B[0m"; // Resetea al color normal
        System.out.println(GREEN + "\n" + string + RESET);
    }

    private void vaciarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void waitForUserInput() {
        showMessage("\n[ Presiona ENTER para continuar ]");
        readString("");
    }

    public void bannerInicio() {
        vaciarPantalla();
        StringBuilder banner = new StringBuilder();
        banner.append("\n");
        banner.append(
                "                          ___   _   ____  _      _      ____  _      _   ___   ___        __   \r\n" + //
                        "                         | |_) | | | |_  | |\\ | \\ \\  / | |_  | |\\ | | | | | \\ / / \\      / /\\  \r\n"
                        + //
                        "                         |_|_) |_| |_|__ |_| \\|  \\_\\/  |_|__ |_| \\| |_| |_|_/ \\_\\_/     /_/--\\ \r\n");
        banner.append("\n");
        banner.append(
                "███████╗██╗  ██╗ █████╗ ███╗   ███╗██╗███╗   ██╗ █████╗ ████████╗ ██████╗ ██████╗     ██████╗  ██████╗  ██████╗  ██████╗ \r\n"
                        + //
                        "██╔════╝╚██╗██╔╝██╔══██╗████╗ ████║██║████╗  ██║██╔══██╗╚══██╔══╝██╔═══██╗██╔══██╗    ╚════██╗██╔═████╗██╔═████╗██╔═████╗\r\n"
                        + //
                        "█████╗   ╚███╔╝ ███████║██╔████╔██║██║██╔██╗ ██║███████║   ██║   ██║   ██║██████╔╝     █████╔╝██║██╔██║██║██╔██║██║██╔██║\r\n"
                        + //
                        "██╔══╝   ██╔██╗ ██╔══██║██║╚██╔╝██║██║██║╚██╗██║██╔══██║   ██║   ██║   ██║██╔══██╗     ╚═══██╗████╔╝██║████╔╝██║████╔╝██║\r\n"
                        + //
                        "███████╗██╔╝ ██╗██║  ██║██║ ╚═╝ ██║██║██║ ╚████║██║  ██║   ██║   ╚██████╔╝██║  ██║    ██████╔╝╚██████╔╝╚██████╔╝╚██████╔╝\r\n"
                        + //
                        "╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝    ╚═════╝  ╚═════╝  ╚═════╝  ╚═════╝ \r\n"
                        + //
                        "                                                                                                                         ");
        banner.append("\n");
        showMessage(banner.toString());
    }

    public void bannerMenuPrincipal() {
        StringBuilder banner = new StringBuilder();
        banner.append("\n");
        banner.append("  __  __ ___ _  _   __    ___ ___ ___ _  _  ___ ___ ___  _   _    \r\n" + //
                " |  \\/  | __| \\| |_/_/_  | _ \\ _ \\_ _| \\| |/ __|_ _| _ \\/_\\ | |   \r\n" + //
                " | |\\/| | _|| .` | |_| | |  _/   /| || .` | (__ | ||  _/ _ \\| |__ \r\n" + //
                " |_|  |_|___|_|\\_|\\___/  |_| |_|_\\___|_|\\_|\\___|___|_|/_/ \\_\\____|\r\n" + //
                "                                                                  ");
        banner.append("\n");
        showMessage(banner.toString());
    }

    public void bannerMenuCRUD() {
        StringBuilder banner = new StringBuilder();
        banner.append("\n");
        banner.append("   ___ ___ _   _ ___    ___                       _           \r\n" + //
                "  / __| _ \\ | | |   \\  | _ \\_ _ ___ __ _ _  _ _ _| |_ __ _ ___\r\n" + //
                " | (__|   / |_| | |) | |  _/ '_/ -_) _` | || | ' \\  _/ _` (_-<\r\n" + //
                "  \\___|_|_\\\\___/|___/  |_| |_| \\___\\__, |\\_,_|_||_\\__\\__,_/__/\r\n" + //
                "                                   |___/                      ");
        banner.append("\n");
        showMessage(banner.toString());
    }

    public void bannerCrearNuevaPregunta() {
        StringBuilder banner = new StringBuilder();
        banner.append("\n");
        banner.append("   ___                                            ___                       _        \r\n" + //
                "  / __|_ _ ___ __ _ _ _   _ _ _  _ _____ ____ _  | _ \\_ _ ___ __ _ _  _ _ _| |_ __ _ \r\n" + //
                " | (__| '_/ -_) _` | '_| | ' \\ || / -_) V / _` | |  _/ '_/ -_) _` | || | ' \\  _/ _` |\r\n" + //
                "  \\___|_| \\___\\__,_|_|   |_||_\\_,_\\___|\\_/\\__,_| |_| |_| \\___\\__, |\\_,_|_||_\\__\\__,_|\r\n"
                + //
                "                                                             |___/                   ");
        banner.append("\n");
        showMessage(banner.toString());
    }

    public void bannerMenuListarPreguntas() {
        StringBuilder banner = new StringBuilder();
        banner.append("\n");
        banner.append("  _    _    _              ___                       _           \r\n" + //
                " | |  (_)__| |_ __ _ _ _  | _ \\_ _ ___ __ _ _  _ _ _| |_ __ _ ___\r\n" + //
                " | |__| (_-<  _/ _` | '_| |  _/ '_/ -_) _` | || | ' \\  _/ _` (_-<\r\n" + //
                " |____|_/__/\\__\\__,_|_|   |_| |_| \\___\\__, |\\_,_|_||_\\__\\__,_/__/\r\n" + //
                "                                      |___/                      ");
        banner.append("\n");
        showMessage(banner.toString());
    }

    public void bannerMenuDetallesPregunta() {
        StringBuilder banner = new StringBuilder();
        banner.append("\n");
        banner.append("  __  __         _ _  __ _               ___                       _        \r\n" + //
                " |  \\/  |___  __| (_)/ _(_)__ __ _ _ _  | _ \\_ _ ___ __ _ _  _ _ _| |_ __ _ \r\n" + //
                " | |\\/| / _ \\/ _` | |  _| / _/ _` | '_| |  _/ '_/ -_) _` | || | ' \\  _/ _` |\r\n" + //
                " |_|  |_\\___/\\__,_|_|_| |_\\__\\__,_|_|   |_| |_| \\___\\__, |\\_,_|_||_\\__\\__,_|\r\n" + //
                "                                                    |___/                   ");
        banner.append("\n");
        showMessage(banner.toString());
    }

    public void bannerMenuImpExp() {
        StringBuilder banner = new StringBuilder();
        banner.append("\n");
        banner.append("  ___                     _                __  ___                   _            \r\n" + //
                " |_ _|_ __  _ __  ___ _ _| |_ __ _ _ _    / / | __|_ ___ __  ___ _ _| |_ __ _ _ _ \r\n" + //
                "  | || '  \\| '_ \\/ _ \\ '_|  _/ _` | '_|  / /  | _|\\ \\ / '_ \\/ _ \\ '_|  _/ _` | '_|\r\n" + //
                " |___|_|_|_| .__/\\___/_|  \\__\\__,_|_|   /_/   |___/_\\_\\ .__/\\___/_|  \\__\\__,_|_|  \r\n" + //
                "           |_|                                        |_|                         ");
        banner.append("\n");
        showMessage(banner.toString());
    }

    public void bannerModoExamen() {
        StringBuilder banner = new StringBuilder();
        banner.append("\n");
        banner.append("  __  __  ___  ___   ___    _____  __   _   __  __ ___ _  _ \r\n" + //
                        " |  \\/  |/ _ \\|   \\ / _ \\  | __\\ \\/ /  /_\\ |  \\/  | __| \\| |\r\n" + //
                        " | |\\/| | (_) | |) | (_) | | _| >  <  / _ \\| |\\/| | _|| .` |\r\n" + //
                        " |_|  |_|\\___/|___/ \\___/  |___/_/\\_\\/_/ \\_\\_|  |_|___|_|\\_|\r\n" + //
                        "                                                            ");
        banner.append("\n");
        showMessage(banner.toString());
    }

    // -------------------------
    // --------- MENÚS ---------
    // -------------------------
    private void mostrarMenuPrincipal() {
        boolean salir = false;
        while (!salir) {
            vaciarPantalla();
            bannerMenuPrincipal();
            showMessage("[1] CRUD de Preguntas");
            showMessage("[2] Exportación/Importación de Preguntas");
            showMessage("[3] Creación de Preguntas Automáticas");
            showMessage("[4] Modo Examen");
            showMessage("[5] Salir");
            String opcion = readString_ne("\n>>> Seleccione una opción -> ");

            switch (opcion) {
                case "1":
                    mostrarMenuCRUD();
                    break;
                case "2":
                    mostrarMenuImpExp();
                    break;
                case "4":
                    mostrarModoExamen();
                    break;
                case "5":
                    salir = true;
                    break;
                default:
                    showErrorMessage("Opción no válida. Intente de nuevo.");
                    waitForUserInput();
            }
        }
    }

    private void mostrarModoExamen() {
        vaciarPantalla();
        bannerModoExamen();
        HashSet<String> temasDisponibles = new HashSet<>();
        try {
            temasDisponibles = controller.startExamMode();
        } catch (RepositoryException e) {
            showErrorMessage("Error al obtener los temas disponibles: " + e.getMessage());
        }
        ArrayList<String> temasLista = new ArrayList<>(temasDisponibles);
        temasLista.add("TODOS LOS TEMAS");

        showMessage("TEMAS A ELEGIR PARA LAS PREGUNTAS DEL EXAMEN");
        for (int i = 1; i <= temasLista.size(); i++) {
            showMessage("[" + i + "] " + temasLista.get(i-1));
        }
        String temaSeleccionado = "";
        boolean encontrado = false;
        do {
            int opcionTema = readInt("\n>>> Seleccione el tema (o todos los temas) de los que se quiera examinar -> ");
            if (opcionTema == temasLista.size()) {
                temaSeleccionado = "TODOS LOS TEMAS";
                showExitoMessage("¡Has seleccionado TODOS LOS TEMAS!");
                encontrado = true;
                break;
            } else
            if (opcionTema >= 1 && opcionTema <= temasLista.size()) {
                temaSeleccionado = temasLista.get(opcionTema - 1);
                showExitoMessage("¡Has seleccionado el tema: " + temaSeleccionado + "!");
                encontrado = true;
                break;
            } else {
                showErrorMessage("Opción no válida. Intente de nuevo.");
            }
        } while (!encontrado);
        int maxPreguntas = 0;
        try {
            maxPreguntas = controller.getMaxQuestions(temaSeleccionado);
        } catch (RepositoryException e) {
            showErrorMessage("Error al obtener el número máximo de preguntas: " + e.getMessage());
        }
        int numPreguntas = readInt(">>> Introduce el número de pregutas que desea (entre 1 y " + maxPreguntas + ") -> ");
        ArrayList<Question> preguntasSeleccionadas = controller.configExam(temaSeleccionado, numPreguntas);
        vaciarPantalla();
        bannerModoExamen();
        showMessage("¡El examen ha comenzado! A continuación se presentan las preguntas seleccionadas:\n");
        // AQUI ME HE QUEDADO MAÑANA MAAASSSS
        waitForUserInput();
    }

    private void mostrarMenuImpExp() {
        boolean salir = false;
        while (!salir) {
            vaciarPantalla();
            bannerMenuImpExp();
            showMessage("[1] Exportar preguntas a un JSON");
            showMessage("[2] Importar preguntas desde un JSON");
            showMessage("[3] Volver al menú principal");
            String opcion = readString_ne("\n>>> Seleccione una opción -> ");

            switch (opcion) {
                case "1":
                    String archivo = readString_ne("\n>>> Ingrese el nombre del archivo de destino (con .json) -> ");
                    try {
                        controller.exportQuestions(archivo);
                        showExitoMessage("Preguntas exportadas exitosamente a " + archivo);
                    } catch (QuestionBackupIOException e) {
                        showErrorMessage("Error al exportar preguntas: " + e.getMessage());
                    } catch (RepositoryException e) {
                        showErrorMessage("Error en el repositorio: " + e.getMessage());
                    }
                    waitForUserInput();
                    break;
                case "2":
                    String archivoImport = readString_ne(
                            "\n>>> Ingrese el nombre del archivo de origen (con .json) -> ");
                    try {
                        controller.importQuestions(archivoImport);
                        showExitoMessage("Preguntas importadas exitosamente desde " + archivoImport);
                    } catch (QuestionBackupIOException e) {
                        showErrorMessage("Error al importar preguntas: " + e.getMessage());
                    } catch (RepositoryException e) {
                        showErrorMessage("Error en el repositorio: " + e.getMessage());
                    }
                    waitForUserInput();
                    break;
                case "3":
                    salir = true;
                    break;
                default:
                    showErrorMessage("Opción no válida. Intente de nuevo.");
                    waitForUserInput();
            }
        }
    }

    private void mostrarMenuCRUD() {
        boolean salir = false;
        while (!salir) {
            vaciarPantalla();
            bannerMenuCRUD();
            showMessage("[1] Crear nueva pregunta");
            showMessage("[2] Listar preguntas existentes");
            showMessage("[3] Ver detalles de una pregunta");
            showMessage("[4] Volver al menú principal");
            String opcion = readString_ne("\n>>> Seleccione una opción -> ");
            switch (opcion) {
                case "1":
                    crearNuevaPregunta();
                    break;
                case "2":
                    mostrarMenuListarPreguntas();
                    break;
                case "3":
                    listarPreguntasEnOrdenDeFecha();
                    mostrarDetallesPregunta();
                    break;
                case "4":
                    salir = true;
                    break;
                default:
                    showErrorMessage("Opción no válida. Intente de nuevo.");
                    waitForUserInput();
            }
        }
    }

    private void mostrarMenuListarPreguntas() {
        boolean salir = false;
        while (!salir) {
            vaciarPantalla();
            bannerMenuListarPreguntas();
            showMessage("[1] Listar todas las preguntas por orden de fecha de creación");
            showMessage("[2] Listar preguntas por tema");
            showMessage("[3] Volver al menú anterior");
            String opcion = readString_ne("\n>>> Seleccione una opción -> ");
            switch (opcion) {
                case "1":
                    listarPreguntasEnOrdenDeFecha();
                    waitForUserInput();
                    break;
                case "2":
                    listarPreguntasPorTema();
                    waitForUserInput();
                    break;
                case "3":
                    salir = true;
                    break;
                default:
                    showErrorMessage("Opción no válida. Intente de nuevo.");
                    waitForUserInput();
            }
        }

    }

    private void mostrarDetallesPregunta() {
        int index = readInt("\n>>> Ingrese el número de la pregunta para ver sus detalles -> ");
        try {
            ArrayList<Question> preguntas = controller.getAllQuestions();
            if (index < 1 || index > preguntas.size()) {
                showErrorMessage("Número de pregunta inválido.");
                waitForUserInput();
            } else {
                mostrarMenuDetallesPregunta(preguntas.get(index - 1));
            }
        } catch (RepositoryException e) {
            showErrorMessage("Error en el repositorio: " + e.getMessage());
            waitForUserInput();
        }

    }

    private void mostrarMenuDetallesPregunta(Question p) {
        boolean salir = false;
        while (!salir) {
            vaciarPantalla();
            bannerMenuDetallesPregunta();
            mostrarPregunta(p, 0);
            showMessage("\n");
            showMessage("[1] Modificar algún atributo de la pregunta");
            showMessage("[2] Eliminar la pregunta");
            showMessage("[3] Volver al menú anterior");
            String opcion = readString_ne("\n>>> Seleccione una opción -> ");
            switch (opcion) {
                case "1":
                    mostrarMenuModificarPregunta(p);
                    waitForUserInput();
                    break;
                case "2":
                    try {
                        controller.removeQuestion(p);
                        showExitoMessage("Pregunta eliminada exitosamente.");
                        salir = true;
                    } catch (RepositoryException e) {
                        showErrorMessage("Error en el repositorio: " + e.getMessage());
                    }
                    waitForUserInput();
                    break;
                case "3":
                    salir = true;
                    break;
                default:
                    showErrorMessage("Opción no válida. Intente de nuevo.");
                    waitForUserInput();
            }
        }
    }

    private void mostrarMenuModificarPregunta(Question p) {
        boolean salir = false;
        while (!salir) {
            vaciarPantalla();
            bannerMenuDetallesPregunta();
            mostrarPregunta(p, 0);
            showMessage("\n");
            showMessage("[1] Modificar autor");
            showMessage("[2] Modificar enunciado");
            showMessage("[3] Modificar temas");
            showMessage("[4] Modificar opciones");
            showMessage("[5] Guardar cambios y volver al menú anterior");
            String opcion = readString_ne("\n>>> Seleccione una opción -> ");

            switch (opcion) {
                case "1":
                    String nuevoAutor = readString_ne(">>> Ingrese el nuevo autor -> ");
                    p.setAuthor(nuevoAutor);
                    showExitoMessage("¡Autor modificado correctamente!");
                    waitForUserInput();
                    break;
                case "2":
                    String nuevoEnunciado = readString_ne(">>> Ingrese el nuevo enunciado -> ");
                    p.setStatement(nuevoEnunciado);
                    showExitoMessage("¡Enunciado modificado correctamente!");
                    waitForUserInput();
                    break;
                case "3":
                    int n = readInt(">>> Ingrese el número de temas de la pregunta -> ");
                    HashSet<String> nuevosTemas = new HashSet<>();
                    for (int i = 0; i < n; i++) {
                        String tema = readString_ne(">>> Ingrese el tema " + (i + 1) + " -> ");
                        tema = tema.toUpperCase();
                        nuevosTemas.add(tema);
                    }
                    p.setTopics(nuevosTemas);
                    showExitoMessage("¡Temas modificados correctamente!");
                    waitForUserInput();
                    break;
                case "4":
                    String opcionA = readString_ne(">>> Ingrese la opción A -> ");
                    String opcionB = readString_ne(">>> Ingrese la opción B -> ");
                    String opcionC = readString_ne(">>> Ingrese la opción C -> ");
                    String opcionD = readString_ne(">>> Ingrese la opción D -> ");
                    String rationaleA = readString_ne(">>> Ingrese la justificación para la opción A -> ");
                    String rationaleB = readString_ne(">>> Ingrese la justificación para la opción B -> ");
                    String rationaleC = readString_ne(">>> Ingrese la justificación para la opción C -> ");
                    String rationaleD = readString_ne(">>> Ingrese la justificación para la opción D -> ");
                    String correctOption;
                    do {
                        correctOption = readString_ne(">>> Ingrese la opción correcta (A/B/C/D) -> ");
                    } while (!correctOption.equalsIgnoreCase("A")
                            && !correctOption.equalsIgnoreCase("B")
                            && !correctOption.equalsIgnoreCase("C")
                            && !correctOption.equalsIgnoreCase("D"));

                    // Crear las opciones
                    List<Option> options = controller.createOptions(opcionA, rationaleA, opcionB, rationaleB, opcionC,
                            rationaleC, opcionD, rationaleD, correctOption);
                    p.setOptions(options);
                    showExitoMessage("¡Opciones modificadas correctamente!");
                    waitForUserInput();
                    break;
                case "5":
                    salir = true;
                    try {
                        controller.modifyQuestion(p);
                    } catch (RepositoryException e) {
                        showErrorMessage("Error en el repositorio: " + e.getMessage());
                    }
                    break;
                default:
                    showErrorMessage("Opción no válida. Intente de nuevo.");
                    waitForUserInput();
            }

        }
    }

    private void crearNuevaPregunta() {
        vaciarPantalla();
        bannerCrearNuevaPregunta();
        showMessage("");
        String author = readString_ne("> Ingrese el autor de la pregunta: ");
        String statement = readString_ne("> Ingrese el enunciado de la pregunta: ");
        int n = readInt("> Ingrese el número de temas de la pregunta: ");
        HashSet<String> topics = new HashSet<>();
        for (int i = 0; i < n; i++) {
            String topic = readString_ne("> Ingrese el tema " + (i + 1) + ": ");
            topic = topic.toUpperCase();
            topics.add(topic);
        }
        String opcionA = readString_ne("> Ingrese la opción A: ");
        String opcionB = readString_ne("> Ingrese la opción B: ");
        String opcionC = readString_ne("> Ingrese la opción C: ");
        String opcionD = readString_ne("> Ingrese la opción D: ");
        String rationaleA = readString_ne("> Ingrese la justificación para la opción A: ");
        String rationaleB = readString_ne("> Ingrese la justificación para la opción B: ");
        String rationaleC = readString_ne("> Ingrese la justificación para la opción C: ");
        String rationaleD = readString_ne("> Ingrese la justificación para la opción D: ");
        String correctOption;
        do {
            correctOption = readString_ne("> Ingrese la opción correcta (A/B/C/D): ");
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
            showExitoMessage("\nPregunta creada y guardada exitosamente.");
        } catch (RepositoryException e) {
            showErrorMessage("Error en el repositorio: " + e.getMessage());
        }
        waitForUserInput();
    }

    private void listarPreguntasPorTema() {
        String tema = readString_ne("\n>>> Ingrese el tema por el cual filtrar las preguntas -> ");
        tema = tema.toUpperCase();
        try {
            List<Question> preguntas = controller.getAllQuestions();
            boolean encontrado = false;
            showMessage("\n[ PREGUNTAS FILTRADAS POR TEMA: " + tema + " ]\n");
            for (int i = 0; i < preguntas.size(); i++) {
                Question q = preguntas.get(i);
                if (q.getTopics().contains(tema)) {
                    mostrarPregunta(q, i + 1);
                    encontrado = true;
                }
            }
            if (!encontrado) {
                showErrorMessage("No se encontraron preguntas para el tema especificado.");
            }
        } catch (RepositoryException e) {
            showErrorMessage("Error en el repositorio: " + e.getMessage());
        }
    }

    private void listarPreguntasEnOrdenDeFecha() {
        vaciarPantalla();
        bannerMenuListarPreguntas();
        try {
            List<Question> preguntas = controller.getAllQuestions();
            if (preguntas.isEmpty()) {
                showErrorMessage("No hay preguntas disponibles.");
                return;
            }
            showMessage("[ PREGUNTAS ORDENDADAS POR FECHA DE CREACIÓN ]\n");

            // Mostrar en el orden que tienen en el ArrayList (sin reordenar)
            for (int i = 0; i < preguntas.size(); i++) {
                mostrarPregunta(preguntas.get(i), i + 1);
            }
        } catch (RepositoryException e) {
            showErrorMessage("Error en el repositorio: " + e.getMessage());
        }
    }

    private void mostrarPregunta(Question q, int index) {
        // Título
        if (index > 0) {
            showMessage("\n================== PREGUNTA " + index + " ==================");
        } else {
            showMessage("\n=============== DETALLES DE LA PREGUNTA ===============");
        }

        // Autor y temas
        showMessage("AUTOR : " + q.getAuthor());
        showMessage("TEMAS : " + String.join(", ", q.getTopics()));

        // Enunciado
        showMessage("\nENUNCIADO: " + q.getStatement());
        // Opciones
        showMessage("\nOPCIONES:");
        List<Option> opts = q.getOptions();
        for (int i = 0; i < opts.size(); i++) {
            Option option = opts.get(i);
            char letter = (char) ('A' + i);
            String bullet = option.isCorrect() ? "[*]" : "[ ]"; // Resalta la correcta
            showMessage(" " + bullet + " " + letter + ") " + option.getText());
        }

        // Separador final
        showMessage("================================================\n");
    }

}
