package model;

import es.usal.genai.GenAiConfig;
import es.usal.genai.GenAiFacade;
import es.usal.genai.SimpleSchemas;
import com.google.genai.types.Schema;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GeminiQuestionCreator implements QuestionCreator {

    // Atributos
    private final String apiKey;
    private final String modelo;
    private final String questionCreatorDescription;

    // Constructor
    public GeminiQuestionCreator(String apiKey, String modelo) {
        this.apiKey = apiKey;
        this.modelo = modelo;
        this.questionCreatorDescription = "Gemini Question Creator - Modelo: " + modelo;
    }

    // Método para crear una pregunta sobre un tema dado usando Gemini
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

                // Convertimos DTO a Question real
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

    // Método para convertir DTO a Question real
    private Question convertToQuestion(QuestionDTO dto, String topic) {
        List<Option> opciones = new ArrayList<>();
        for (int i = 0; i < dto.options.size(); i++) {
            OptionDTO optDto = dto.options.get(i);
            String label = switch (i) {
                case 0 -> "A";
                case 1 -> "B";
                case 2 -> "C";
                case 3 -> "D";
                default -> "X";
            };
            opciones.add(new Option(optDto.text, optDto.rationale, optDto.correct, label));
        }

        HashSet<String> topicsSet = new HashSet<>();
        topicsSet.add(topic);

        return new Question(this.modelo, topicsSet, dto.statement, opciones);
    }
}
