package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class BinaryRepository implements IRepository {

    // Ruta del archivo binario en el directorio del usuario
    private Path ruta = Paths.get(System.getProperty("user.home"), "questions.bin");

    // Lista de preguntas en memoria
    private ArrayList<Question> questions;

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    // Método para agregar una pregunta al repositorio
    @Override
    public void addQuestion(Question q) throws RepositoryException {
        if (q == null) {
            throw new RepositoryException("La pregunta es nula.");
        }

        // Asegurar que la lista en memoria esté inicializada
        if (questions == null) {
            questions = getAllQuestions();
        }

        // Comprobar duplicados por id
        if (q.getId() != null) {
            for (Question existing : questions) {
                if (existing != null && existing.getId() != null && existing.getId().equals(q.getId())) {
                    return;
                }
            }
        } else {
            return; // No se permite agregar preguntas sin id
        }

        // Añadir y guardar
        questions.add(q);
    }

    // Método para eliminar una pregunta del repositorio
    @Override
    public void removeQuestion(Question q) throws RepositoryException {
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getId().equals(q.getId())) {
                questions.remove(i);
                break;
            }
        }
    }

    // Método para modificar una pregunta en el repositorio
    @Override
    public void modifyQuestion(Question q) throws RepositoryException {
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getId().equals(q.getId())) {
                questions.set(i, q);
                break;
            }
        }
    }

    // Método para obtener todas las preguntas (cargando desde el repositorio si es necesario)
    @Override
    public ArrayList<Question> getAllQuestions() throws RepositoryException {
        if (questions == null) {
            questions = getAllQuestionsFromRepository();
        }
        return questions;
    }

    // Método para obtener todas las preguntas del repositorio
    @Override
    public ArrayList<Question> getAllQuestionsFromRepository() throws RepositoryException {
        // Si el archivo no existe, inicializar lista vacía
        if (!Files.exists(ruta)) {
            questions = new ArrayList<>();
            return questions;
        }
        // Leer el archivo binario
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(ruta))) {
            Object obj = ois.readObject();

            if (!(obj instanceof ArrayList<?> list)) {
                throw new RepositoryException("El archivo no contiene una lista válida de preguntas.");
            }

            ArrayList<Question> loaded = new ArrayList<>();
            for (Object item : list) {
                if (item instanceof Question q) {
                    loaded.add(q);
                }
            }

            questions = loaded;
            return questions;

        } catch (IOException | ClassNotFoundException e) {
            throw new RepositoryException("Error al leer las preguntas: " + e.getMessage(), e);
        }
    }

    // Método para guardar la lista de preguntas en el archivo binario
    @Override
    public void saveQuestions() throws RepositoryException {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(ruta))) {
            oos.writeObject(questions);
        } catch (IOException e) {
            throw new RepositoryException("Error al guardar las preguntas: " + e.getMessage(), e);
        }
    }
}
