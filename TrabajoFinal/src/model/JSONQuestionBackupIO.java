package model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.google.gson.Gson;

public class JSONQuestionBackupIO implements QuestionBackupIO {

    Path ruta;

    public void setRuta(String ruta) {
        if (ruta == null || ruta.trim().isEmpty()) {
            this.ruta = Paths.get(System.getProperty("user.home"));
        } else {
            this.ruta = Paths.get(ruta);
        }
    }

    @Override
    public void exportQuestions(List<Question> questions, String archivo) throws QuestionBackupIOException {
        Gson gson = new Gson();
        String json = gson.toJson(questions);
        Path base = (this.ruta != null) ? this.ruta : Paths.get(System.getProperty("user.home"));
        Path target = base.resolve(archivo);
        try {
            // Asegura que el directorio existe
            if (Files.notExists(base)) {
                Files.createDirectories(base);
            }
            Files.write(target, json.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new QuestionBackupIOException("Error al exportar preguntas a " + target + ": " + e.getMessage());
        }
    }

    @Override
    public String getBackupIODescription() throws QuestionBackupIOException {
        return "JSON Backup IO";
    }

    @Override
    public List<Question> importQuestions() throws QuestionBackupIOException {
        // Implementación pendiente
        return null;    
    }

}
