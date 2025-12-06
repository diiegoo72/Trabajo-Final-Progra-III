package model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class JSONQuestionBackupIO implements QuestionBackupIO {

    Path ruta = Paths.get(System.getProperty("user.home"));

    public void setRuta(String ruta) {
        if (ruta == null || ruta.trim().isEmpty()) {
            this.ruta = Paths.get(System.getProperty("user.home"));
        } else {
            this.ruta = Paths.get(ruta);
        }
    }

    @Override
    public void exportQuestions(ArrayList<Question> questions, String archivo) throws QuestionBackupIOException {

        // 1. Validar nombre
        if (archivo == null || archivo.isBlank()) {
            throw new QuestionBackupIOException("El nombre del archivo no puede estar vacío.");
        }
        if (Paths.get(archivo).getParent() != null) {
            throw new QuestionBackupIOException("El parámetro 'archivo' debe ser solo un nombre de archivo (sin carpetas).");
        }

        // 2. Generar JSON con formato bonito
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(questions);

        // 3. Guardar SIEMPRE en el HOME
        Path target = this.ruta.resolve(archivo);

        try {
            Files.writeString(target, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new QuestionBackupIOException("Error al exportar preguntas a JSON: " + target, e);
        }
    }

    @Override
    public String getBackupIODescription() throws QuestionBackupIOException {
        return "JSON Backup IO";
    }

    @Override
    public ArrayList<Question> importQuestions(String archivo) throws QuestionBackupIOException {

        // 1. Validar nombre
        if (archivo == null || archivo.isBlank()) {
            throw new QuestionBackupIOException("El nombre del archivo no puede estar vacío.");
        }
        if (Paths.get(archivo).getParent() != null) {
            throw new QuestionBackupIOException("El nombre del archivo no debe contener rutas, solo el nombre.");
        }

        // 2. Obtener ruta en el home del usuario SIEMPRE
        Path ruta = this.ruta.resolve(archivo); // <--- SIEMPRE en HOME

        // 3. Validar existencia
        if (!Files.exists(ruta) || Files.isDirectory(ruta)) {
            throw new QuestionBackupIOException("El archivo no existe o es un directorio: " + ruta);
        }

        try {
            // 4. Leer archivo JSON
            String json = Files.readString(ruta, StandardCharsets.UTF_8);

            // 5. Parsear JSON
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Question>>() {}.getType();
            ArrayList<Question> questions = gson.fromJson(json, listType);

            // 6. Devolver lista NO nula
            return (questions != null) ? questions : new ArrayList<>();

        } catch (IOException e) {
            throw new QuestionBackupIOException("No se pudo leer el archivo de importación: " + ruta, e);
        } catch (JsonSyntaxException e) {
            throw new QuestionBackupIOException("El archivo JSON está mal formado: " + ruta, e);
        }
    }

}
