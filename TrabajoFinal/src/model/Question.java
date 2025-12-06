package model;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.io.Serializable;

public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    // Atributos
    private UUID id;
    private String author;
    private HashSet<String> topics;
    private String statement;
    private List<Option> options;


    // Constructor
    public Question(String author, HashSet<String> topics, String statement,
            List<Option> options) {
        this.id = UUID.randomUUID();
        this.author = author;
        this.topics = topics;
        this.statement = statement;
        this.options = options;
    }

    
    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }



    public String getAuthor() {
        return author;
    }



    public void setAuthor(String author) {
        this.author = author;
    }


    public HashSet<String> getTopics() {
        return topics;
    }



    public void setTopics(HashSet<String> topics) {
        this.topics = topics;
    }



    public String getStatement() {
        return statement;
    }



    public void setStatement(String statement) {
        this.statement = statement;
    }



    public List<Option> getOptions() {
        return options;
    }



    public void setOptions(List<Option> options) {
        this.options = options;
    }


    public boolean isCorrectOption(String respuesta) {
        for (Option option : options) {
            if (option.getText().equalsIgnoreCase(respuesta)) {
                return option.isCorrect();
            }
        }
        return false;
    }


    public String getRationaleForOption(String respuesta) {
        for (Option option : options) {
            if (option.getText().equalsIgnoreCase(respuesta)) {
                return option.getRationale();
            }
        }
        return "No rationale available.";
    }

    
}
