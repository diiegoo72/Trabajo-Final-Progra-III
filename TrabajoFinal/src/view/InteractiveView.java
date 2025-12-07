package view;

import static com.coti.tools.Esdia.readInt;
import static com.coti.tools.Esdia.readString_ne;
import static com.coti.tools.Esdia.readString;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import model.Examen;
import model.Option;
import model.Question;
import model.QuestionBackupIOException;
import model.QuestionCreatorException;
import model.RepositoryException;

public class InteractiveView extends BaseView {

    // Códigos de colores para la consola
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";

    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String RED = "\u001B[31m";
    private static final String ORANGE = "\u001B[38;5;208m";

    // Método de finalización de la vista interactiva
    @Override
    protected void end() {
        try {
            controller.end();
            showGoodMessage(BLUE + "¡Preguntas guardadas correctamente en el repositorio!" + RESET);
        } catch (RepositoryException e) {
            e.printStackTrace();
            showErrorMessage("Error al guardar las preguntas en el repositorio: " + e.getMessage());
        }
        showGoodMessage("Gracias por usar la aplicación. ¡Hasta pronto!");
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
            showErrorMessage("Error al cargar las preguntas del repositorio: " + e.getMessage());
        }

        // Mostrar mensaje de carga de preguntas
        if (questions != null && !questions.isEmpty()) {
            showGoodMessage("Se han cargado " + questions.size() + " preguntas desde el repositorio.");
        } else {
            showMessage("No hay preguntas en el repositorio binario del home del usuario.");
        }
        waitForUserInput();
        // Mostrar el menú principal
        mostrarMenuPrincipal();
        // Finalizar la vista al salir
        end();
    }

    // Método para mostrar un mensaje de error
    @Override
    protected void showErrorMessage(String msg) {
        System.err.println(RED + "\n" + msg + RESET);
    }

    // Método para mostrar un mensaje informativo
    @Override
    protected void showMessage(String msg) {
        System.out.println(msg);
    }

    // Método para mostrar un mensaje de éxito
    @Override
    protected void showGoodMessage(String string) {
        System.out.println(GREEN + "\n" + string + RESET);
    }

    // Método para vaciar la pantalla de la consola
    private void vaciarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Método para esperar un input del usuario
    private void waitForUserInput() {
        showMessage("\n[ Presiona ENTER para continuar ]");
        readString("");
    }

    // Modo de visualización de preguntas
    public enum ModoPregunta {
        COMPLETA,
        SIMPLE
    }

    // Método para mostrar una pregunta en detalle o de forma simple
    @Override
    protected void mostrarPregunta(Question q, int index, ModoPregunta modo) {
        DateTimeFormatter FECHA_FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        boolean detallado = (modo == ModoPregunta.COMPLETA);

        // Título
        if (index > 0) {
            showMessage(CYAN + BOLD + "\n================== PREGUNTA " + index + " ==================" + RESET);
        } else {
            showMessage(CYAN + BOLD + "\n=============== DETALLES DE LA PREGUNTA ================" + RESET);
        }

        // Autor y temas
        if (detallado) {
            showMessage(PURPLE + BOLD + "AUTOR : " + RESET + q.getAuthor());
        }
        showMessage(PURPLE + BOLD + "TEMA/S : " + RESET + String.join(", ", q.getTopics()));
        showMessage(PURPLE + BOLD + "FECHA DE CREACIÓN : " + RESET + q.getCreationDate().format(FECHA_FORMATO));

        // Enunciado
        showMessage(YELLOW + BOLD + "\nENUNCIADO: " + RESET + q.getStatement());

        // Opciones
        showMessage(BLUE + BOLD + "\nOPCIONES:" + RESET);
        List<Option> opts = q.getOptions();

        for (int i = 0; i < opts.size(); i++) {
            Option option = opts.get(i);
            char letter = (char) ('A' + i);

            boolean correcta = option.isCorrect();

            // Solo en modo detallado la opción correcta se ve en verde
            String color = (detallado && correcta) ? GREEN + BOLD : RESET;

            showMessage(" " + color + letter + ") " + option.getText() + RESET);
        }

        // Justificaciones solo en modo detallado
        if (detallado) {
            showMessage(PURPLE + BOLD + "\nJUSTIFICACIONES:" + RESET);
            for (Option option : q.getOptions()) {
                String color = option.isCorrect() ? GREEN : RESET;
                showMessage(" " + color + "- " + option.getRationale() + RESET);
            }
        }

        showMessage(CYAN + BOLD + "\n================================================\n" + RESET);
    }

    // BANNERS DE LAS DISTINTAS SECCIONES
    private void bannerInicio() {
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

    private void bannerMenuPrincipal() {
        StringBuilder banner = new StringBuilder();
        banner.append("\n");
        banner.append("  __  __ ___ _  _   __    ___ ___ ___ _  _  ___ ___ ___  _   _    \r\n" + //
                " |  \\/  | __| \\| |_/_/_  | _ \\ _ \\_ _| \\| |/ __|_ _| _ \\/_\\ | |   \r\n" + //
                " | |\\/| | _|| .` | |_| | |  _/   /| || .` | (__ | ||  _/ _ \\| |__ \r\n" + //
                " |_|  |_|___|_|\\_|\\___/  |_| |_|_\\___|_|\\_|\\___|___|_|/_/ \\_\\____|\r\n" + //
                "                                                                  ");
        banner.append("\n");
        showMessage(YELLOW + BOLD + banner.toString() + RESET);
    }

    private void bannerMenuCRUD() {
        StringBuilder banner = new StringBuilder();
        banner.append("\n");
        banner.append("   ___ ___ _   _ ___    ___                       _           \r\n" + //
                "  / __| _ \\ | | |   \\  | _ \\_ _ ___ __ _ _  _ _ _| |_ __ _ ___\r\n" + //
                " | (__|   / |_| | |) | |  _/ '_/ -_) _` | || | ' \\  _/ _` (_-<\r\n" + //
                "  \\___|_|_\\\\___/|___/  |_| |_| \\___\\__, |\\_,_|_||_\\__\\__,_/__/\r\n" + //
                "                                   |___/                      ");
        banner.append("\n");
        showMessage(ORANGE + BOLD + banner.toString() + RESET);
    }

    private void bannerCrearNuevaPregunta() {
        StringBuilder banner = new StringBuilder();
        banner.append("\n");
        banner.append("   ___                                            ___                       _        \r\n" + //
                "  / __|_ _ ___ __ _ _ _   _ _ _  _ _____ ____ _  | _ \\_ _ ___ __ _ _  _ _ _| |_ __ _ \r\n" + //
                " | (__| '_/ -_) _` | '_| | ' \\ || / -_) V / _` | |  _/ '_/ -_) _` | || | ' \\  _/ _` |\r\n" + //
                "  \\___|_| \\___\\__,_|_|   |_||_\\_,_\\___|\\_/\\__,_| |_| |_| \\___\\__, |\\_,_|_||_\\__\\__,_|\r\n"
                + //
                "                                                             |___/                   ");
        banner.append("\n");
        showMessage(BLUE + BOLD + banner.toString() + RESET);
    }

    private void bannerMenuListarPreguntas() {
        StringBuilder banner = new StringBuilder();
        banner.append("\n");
        banner.append("  _    _    _              ___                       _           \r\n" + //
                " | |  (_)__| |_ __ _ _ _  | _ \\_ _ ___ __ _ _  _ _ _| |_ __ _ ___\r\n" + //
                " | |__| (_-<  _/ _` | '_| |  _/ '_/ -_) _` | || | ' \\  _/ _` (_-<\r\n" + //
                " |____|_/__/\\__\\__,_|_|   |_| |_| \\___\\__, |\\_,_|_||_\\__\\__,_/__/\r\n" + //
                "                                      |___/                      ");
        banner.append("\n");
        showMessage(PURPLE + BOLD + banner.toString() + RESET);
    }

    private void bannerMenuDetallesPregunta() {
        StringBuilder banner = new StringBuilder();
        banner.append("\n");
        banner.append("  __  __         _ _  __ _               ___                       _        \r\n" + //
                " |  \\/  |___  __| (_)/ _(_)__ __ _ _ _  | _ \\_ _ ___ __ _ _  _ _ _| |_ __ _ \r\n" + //
                " | |\\/| / _ \\/ _` | |  _| / _/ _` | '_| |  _/ '_/ -_) _` | || | ' \\  _/ _` |\r\n" + //
                " |_|  |_\\___/\\__,_|_|_| |_\\__\\__,_|_|   |_| |_| \\___\\__, |\\_,_|_||_\\__\\__,_|\r\n" + //
                "                                                    |___/                   ");
        banner.append("\n");
        showMessage(CYAN + BOLD + banner.toString() + RESET);
    }

    private void bannerMenuImpExp() {
        StringBuilder banner = new StringBuilder();
        banner.append("\n");
        banner.append("  ___                     _                __  ___                   _            \r\n" + //
                " |_ _|_ __  _ __  ___ _ _| |_ __ _ _ _    / / | __|_ ___ __  ___ _ _| |_ __ _ _ _ \r\n" + //
                "  | || '  \\| '_ \\/ _ \\ '_|  _/ _` | '_|  / /  | _|\\ \\ / '_ \\/ _ \\ '_|  _/ _` | '_|\r\n" + //
                " |___|_|_|_| .__/\\___/_|  \\__\\__,_|_|   /_/   |___/_\\_\\ .__/\\___/_|  \\__\\__,_|_|  \r\n" + //
                "           |_|                                        |_|                         ");
        banner.append("\n");
        showMessage(ORANGE + BOLD + banner.toString() + RESET);
    }

    private void bannerModoExamen() {
        StringBuilder banner = new StringBuilder();
        banner.append("\n");
        banner.append("  __  __  ___  ___   ___    _____  __   _   __  __ ___ _  _ \r\n" + //
                " |  \\/  |/ _ \\|   \\ / _ \\  | __\\ \\/ /  /_\\ |  \\/  | __| \\| |\r\n" + //
                " | |\\/| | (_) | |) | (_) | | _| >  <  / _ \\| |\\/| | _|| .` |\r\n" + //
                " |_|  |_|\\___/|___/ \\___/  |___/_/\\_\\/_/ \\_\\_|  |_|___|_|\\_|\r\n" + //
                "                                                            ");
        banner.append("\n");
        showMessage(ORANGE + BOLD + banner.toString() + RESET);
    }

    // MENÚ PRINCIPAL
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
                case "3":
                    crearPreguntaAutomatica();
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

    // Menú CRUD de preguntas
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

    // Crear una nueva pregunta
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
            showGoodMessage("\nPregunta creada y guardada exitosamente.");
        } catch (RepositoryException e) {
            showErrorMessage("Error en el repositorio: " + e.getMessage());
        }
        waitForUserInput();
    }

    // Menú para listar preguntas
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

    // Listar preguntas filtradas por tema
    @Override
    protected void listarPreguntasPorTema() {
        // Temas disponibles
        HashSet<String> temasDisponibles = new HashSet<>();
        try {
            temasDisponibles = controller.getAvailableTopics();
        } catch (RepositoryException e) {
            showErrorMessage("Error al obtener los temas: " + e.getMessage());
            return;
        }

        ArrayList<String> temasLista = new ArrayList<>(temasDisponibles);

        if (temasLista.isEmpty()) {
            showErrorMessage("No hay preguntas disponibles en el repositorio.");
            return;
        }

        showMessage(BLUE + "\n --- TEMAS A ELEGIR ---" + RESET);
        for (int i = 1; i <= temasLista.size(); i++) {
            showMessage(temasLista.get(i - 1));
        }
        // Selección de tema
        String tema = readString_ne("\n>>> Ingrese el tema por el cual filtrar las preguntas -> ");
        tema = tema.toUpperCase();

        // Listar preguntas del tema seleccionado
        try {
            List<Question> preguntas = controller.getAllQuestions();
            boolean encontrado = false;
            showMessage("\n[ PREGUNTAS FILTRADAS POR TEMA: " + tema + " ]\n");
            for (int i = 0; i < preguntas.size(); i++) {
                Question q = preguntas.get(i);
                if (q.getTopics().contains(tema)) {
                    mostrarPregunta(q, i + 1, ModoPregunta.SIMPLE);
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

    // Listar todas las preguntas por orden de fecha de creación
    @Override
    protected void listarPreguntasEnOrdenDeFecha() {
        vaciarPantalla();
        bannerMenuListarPreguntas();
        try {
            List<Question> preguntas = controller.getAllQuestions();
            if (preguntas.isEmpty()) {
                showErrorMessage("No hay preguntas disponibles.");
                return;
            }
            showMessage("[ PREGUNTAS ORDENADAS POR FECHA DE CREACIÓN ]\n");

            // Ordenamos por creationDate (más antiguas → más nuevas)
            preguntas.sort((p1, p2) -> p1.getCreationDate().compareTo(p2.getCreationDate()));

            for (int i = 0; i < preguntas.size(); i++) {
                mostrarPregunta(preguntas.get(i), i + 1, ModoPregunta.SIMPLE);
            }
        } catch (RepositoryException e) {
            showErrorMessage("Error en el repositorio: " + e.getMessage());
        }
    }

    // Mostrar detalles de una pregunta específica
    private void mostrarDetallesPregunta() {
        try {
            ArrayList<Question> preguntas = controller.getAllQuestions();
            if (preguntas.isEmpty()) {
                showErrorMessage("No hay preguntas disponibles en el repositorio.");
                waitForUserInput();
                return;
            }
            int index = readInt("\n>>> Ingrese el número de la pregunta para ver sus detalles -> ");
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

    // Menú para ver/modificar/eliminar una pregunta específica
    private void mostrarMenuDetallesPregunta(Question p) {
        boolean salir = false;
        while (!salir) {
            vaciarPantalla();
            bannerMenuDetallesPregunta();
            mostrarPregunta(p, 0, ModoPregunta.COMPLETA);
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
                        showGoodMessage("Pregunta eliminada exitosamente.");
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

    // Menú para modificar los atributos de una pregunta específica
    private void mostrarMenuModificarPregunta(Question p) {
        boolean salir = false;
        while (!salir) {
            vaciarPantalla();
            bannerMenuDetallesPregunta();
            mostrarPregunta(p, 0, ModoPregunta.COMPLETA);
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
                    showGoodMessage("¡Autor modificado correctamente!");
                    waitForUserInput();
                    break;
                case "2":
                    String nuevoEnunciado = readString_ne(">>> Ingrese el nuevo enunciado -> ");
                    p.setStatement(nuevoEnunciado);
                    showGoodMessage("¡Enunciado modificado correctamente!");
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
                    showGoodMessage("¡Temas modificados correctamente!");
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
                    showGoodMessage("¡Opciones modificadas correctamente!");
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

    private void crearPreguntaAutomatica() {
        vaciarPantalla();
        bannerCrearNuevaPregunta();

        ArrayList<String> modelos = controller.getModelosDisponibles();

        showMessage("--- MODELOS DE IA DISPONIBLES PARA GENERAR PREGUNTAS ---\n");
        if (modelos.isEmpty()) {
            showErrorMessage("No hay modelos de IA disponibles para generar preguntas.\n");
            showMessage("Para poder usar esta funcionalidad, debe ejecutar la aplicación escribiendo: \n");
            showMessage("java -jar app.jar -question-creator 'modelo_IA' 'API_KEY'\n");
            waitForUserInput();
            return;
        }
        for (int i = 0; i < modelos.size(); i++) {
            showMessage("[" + (i + 1) + "] " + modelos.get(i));
        }
        int modeloIndex;
        String selectedModel;
        while (true) {
            modeloIndex = readInt("\n>>> Seleccione el modelo de IA para generar la pregunta -> ");
            if (modeloIndex >= 1 && modeloIndex <= modelos.size()) {
                selectedModel = modelos.get(modeloIndex - 1);
                showGoodMessage("¡Has seleccionado el modelo: " + selectedModel + "!");
                break;
            } else {
                showErrorMessage("Opción no válida. Intente de nuevo.");
            }
        }

        String tema = readString_ne("\n>>> Introduce el tema de la pregunta -> ");
        tema = tema.toUpperCase();

        try {
            Question preguntaGenerada = controller.createQuestion(tema, selectedModel);
            controller.addQuestion(preguntaGenerada);
            mostrarPregunta(preguntaGenerada, 0, ModoPregunta.COMPLETA);
            showGoodMessage("¡Pregunta generada y añadida exitosamente al repositorio!");
        } catch (RepositoryException e) {
            showErrorMessage("Error al añadir la pregunta al repositorio: " + e.getMessage());
        } catch (QuestionCreatorException e) {
            showErrorMessage("Error al generar la pregunta automática: " + e.getMessage());
        }

        waitForUserInput();
    }

    private void mostrarModoExamen() {
        vaciarPantalla();
        bannerModoExamen();
        // Temas disponibles
        HashSet<String> temasDisponibles = new HashSet<>();
        try {
            temasDisponibles = controller.getAvailableTopics();
        } catch (RepositoryException e) {
            showErrorMessage("Error al obtener los temas: " + e.getMessage());
            return;
        }

        ArrayList<String> temasLista = new ArrayList<>(temasDisponibles);
        temasLista.add("TODOS LOS TEMAS");

        showMessage("TEMAS A ELEGIR PARA EL EXAMEN:");
        for (int i = 1; i <= temasLista.size(); i++) {
            showMessage("[" + i + "] " + temasLista.get(i - 1));
        }

        // Selección de tema
        String temaSeleccionado = "";
        while (true) {
            int opcionTema = readInt("\n>>> Seleccione el tema (o todos los temas) -> ");
            if (opcionTema == temasLista.size()) {
                temaSeleccionado = "TODOS LOS TEMAS";
                showGoodMessage("¡Has seleccionado TODOS LOS TEMAS!");
                break;
            } else if (opcionTema >= 1 && opcionTema <= temasLista.size()) {
                temaSeleccionado = temasLista.get(opcionTema - 1);
                showGoodMessage("¡Has seleccionado el tema: " + temaSeleccionado + "!");
                break;
            } else {
                showErrorMessage("Opción no válida. Intente de nuevo.");
            }
        }

        // Número de preguntas
        int maxPreguntas;
        try {
            maxPreguntas = controller.getMaxQuestions(temaSeleccionado);
        } catch (RepositoryException e) {
            showErrorMessage("Error al obtener el número máximo de preguntas: " + e.getMessage());
            return;
        }

        int numPreguntas;
        do {
            numPreguntas = readInt(">>> Introduce el número de preguntas (1 - " + maxPreguntas + ") -> ");
            if (numPreguntas < 1 || numPreguntas > maxPreguntas) {
                showErrorMessage("Número inválido. Intente de nuevo.");
            }
        } while (numPreguntas < 1 || numPreguntas > maxPreguntas);

        showGoodMessage("¡Has seleccionado " + numPreguntas + " preguntas para el examen!");

        // Iniciar examen
        controller.iniciarExamen(temaSeleccionado, numPreguntas);

        // Preguntas y respuestas
        for (int i = 0; i < numPreguntas; i++) {
            vaciarPantalla();
            bannerModoExamen();

            Question q = controller.getPregunta(i);
            mostrarPregunta(q, i + 1, ModoPregunta.SIMPLE);

            String respuesta;
            while (true) {
                respuesta = readString(">>> Ingrese su respuesta (A/B/C/D) o pulsa INTRO para no responder -> ");
                if (respuesta.equalsIgnoreCase("A") || respuesta.equalsIgnoreCase("B") ||
                        respuesta.equalsIgnoreCase("C") || respuesta.equalsIgnoreCase("D") || respuesta.isEmpty()) {
                    break;
                } else {
                    showErrorMessage("Respuesta no válida. Intente de nuevo.");
                }
            }

            String resultado = controller.responderPregunta(i, respuesta);
            showMessage(resultado);
            waitForUserInput();
        }

        // Resultados finales
        controller.finalizarExamen();

        vaciarPantalla();
        bannerModoExamen();
        showMessage("¡Examen finalizado! Aquí están tus resultados:\n");

        Examen examen = controller.getExamen();
        showMessage(examen.getResumen());

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
                        showGoodMessage("Preguntas exportadas exitosamente a " + archivo);
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
                        showGoodMessage("Preguntas importadas exitosamente desde " + archivoImport);
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

}
