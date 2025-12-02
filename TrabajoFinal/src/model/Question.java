package model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.io.Serializable;

public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    // Atributos
    private UUID id;
    private String author;
    private LocalDate date;
    private HashSet<String> topics;
    private String statement;
    private List<Option> options;


    // Constructor
    public Question(String author, HashSet<String> topics, String statement,
            List<Option> options) {
        this.id = UUID.randomUUID();
        this.author = author;
        this.date = LocalDate.now();
        this.topics = topics;
        this.statement = statement;
        this.options = options;
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



    public LocalDate getDate() {
        return date;
    }



    public void setDate(LocalDate date) {
        this.date = date;
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



    public Object getId() {
        return id;
    }    

    
}
