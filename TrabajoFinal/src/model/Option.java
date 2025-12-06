package model;
import java.io.Serializable;

public class Option implements Serializable {
    // Atributos
    private String text;
    private String rationale;
    private boolean correct;
    private String userAnswer;

    // Constructor
    public Option(String text, String rationale, boolean correct, String userAnswer) {
        this.text = text;
        this.rationale = rationale;
        this.correct = correct;
        this.userAnswer = userAnswer;
    }

    // Getters y Setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRationale() {
        return rationale;
    }

    public void setRationale(String rationale) {
        this.rationale = rationale;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
    
}
