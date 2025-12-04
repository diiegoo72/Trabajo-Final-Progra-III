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

    // Método privado para guardar la lista de preguntas en el archivo binario
    private boolean savePreguntas(ArrayList<Question> preguntas) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(Files.newOutputStream(ruta));
            oos.writeObject(preguntas);
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

    @Override
    public Question addQuestion(Question q) {
        ArrayList<Question> preguntas = getAllQuestions();
        preguntas.add(q);
        savePreguntas(preguntas);
        return q;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<Question> getAllQuestions() {
        if (!Files.exists(ruta)) {
            return new ArrayList<>();
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(Files.newInputStream(ruta));
            return (ArrayList<Question>) ois.readObject();
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

    @Override
    public void modifyQuestion(Question q) {
        ArrayList<Question> preguntas = getAllQuestions();
        for (int i = 0; i < preguntas.size(); i++) {
            if (preguntas.get(i).getId().equals(q.getId())) {
                preguntas.set(i, q);
                break;
            }
        }
        savePreguntas(preguntas);
    }

    @Override
    public void removeQuestion(Question q) {
        ArrayList<Question> preguntas = getAllQuestions();
        for (int i = 0; i < preguntas.size(); i++) {
            if (preguntas.get(i).getId().equals(q.getId())) {
                preguntas.remove(i);
                break;
            }
        }
        savePreguntas(preguntas);
    }

}
