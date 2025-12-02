package model;


import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


public class BinaryRepository implements IRepository {

    Path ruta = java.nio.file.Paths.get(System.getProperty("user.home"), "Desktop", "questions.bin");

        private boolean savePreguntas(ArrayList<Question> preguntas) {
        java.io.ObjectOutputStream oos = null;
        try {
            oos = new java.io.ObjectOutputStream(Files.newOutputStream(ruta));
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
        java.io.ObjectInputStream ois = null;
        try {
            ois = new java.io.ObjectInputStream(java.nio.file.Files.newInputStream(ruta));
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

        /// TAMAAAAALLLLLLLLLLLLLLLLLLLLLLLL
    @Override
    public Question modifyQuestion(Question q) {
        return null;
    }

    @Override
    public void removeQuestion(Question q) {
        
    }

    

    
}
