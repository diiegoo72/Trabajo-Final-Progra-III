package model;

import es.usal.genai.GenAiConfig;
import es.usal.genai.GenAiFacade;
import es.usal.genai.SimpleSchemas;
import com.google.genai.types.Schema;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GeminiQuestionCreator implements QuestionCreator {

    private final String apiKey;
    private final String modelo;
    private final String questionCreatorDescription;

    public GeminiQuestionCreator(String apiKey, String modelo) {
        this.apiKey = apiKey;
        this.modelo = modelo;
        this.questionCreatorDescription = "Gemini Question Creator - Modelo: " + modelo;
    }

    @Override
    public Question createQuestion(String topic) throws QuestionCreatorException {
        try {
            // Configuración de la SDK usando el modelo
            GenAiConfig config = GenAiConfig.forGemini(modelo, apiKey);

            try (GenAiFacade genai = new GenAiFacade(config)) {
                // Prompt para generar la pregunta de 4 opciones
                String prompt = "Crea una pregunta de tipo test sobre el tema: \"" + topic + "\". " +
                        "Debe tener 4 opciones, solo una correcta y cada opción con su justificación. " +
                        "Devuélvelo en JSON con esta estructura: { \"statement\": \"...\", " +
                        "\"options\": [ { \"text\": \"...\", \"correct\": true/false, \"rationale\": \"...\" }, ... ] }";

                // Creamos el esquema a partir de nuestro DTO
                Schema schema = SimpleSchemas.from(QuestionDTO.class);

                // Generamos el objeto directamente usando generateJson
                QuestionDTO dto = genai.generateJson(prompt, schema, QuestionDTO.class);

                // Convertimos DTO a Question real, pasando el tema para topics
                return convertToQuestion(dto, topic);
            }

        } catch (Exception e) {
            throw new QuestionCreatorException("Error creando la pregunta con Gemini: " + e.getMessage(), e);
        }
    }

    @Override
    public String getQuestionCreatorDescription() {
        return questionCreatorDescription;
    }

    // Convierte DTO a Question usando modelo y topic
    private Question convertToQuestion(QuestionDTO dto, String topic) {
        List<Option> opciones = new ArrayList<>();
        for (OptionDTO o : dto.options) {
            opciones.add(new Option(o.text, o.rationale, o.correct, "")); // userAnswer vacío
        }

        HashSet<String> topicsSet = new HashSet<>();
        topicsSet.add(topic);

        // author = modelo usado, topics = tema
        return new Question(this.modelo, topicsSet, dto.statement, opciones);
    }
}
