package model;

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

    // Método para agregar una pregunta al repositorio
    @Override
    public Question addQuestion(Question q) throws RepositoryException {
        questions.add(q);
        saveQuestions(questions);
        return q;
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
        saveQuestions(questions);
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
        saveQuestions(questions);
    }

    // Método para obtener todas las preguntas del repositorio
    @Override
    public ArrayList<Question> getAllQuestions() throws RepositoryException {
        if (!Files.exists(ruta)) {
            return new ArrayList<>();
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(Files.newInputStream(ruta));
            Object obj = ois.readObject();
            ArrayList<Question> loaded = new ArrayList<>();
            if (obj instanceof ArrayList<?>) {
                for (Object item : (ArrayList<?>) obj) {
                    if (item instanceof Question) {
                        loaded.add((Question) item);
                    }
                }
            }
            questions = loaded;
            return questions;
        } catch (java.io.IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Método para guardar la lista de preguntas en el archivo binario
    @Override
    public boolean saveQuestions(ArrayList<Question> questions) throws RepositoryException {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(Files.newOutputStream(ruta));
            oos.writeObject(questions);
            return true;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
