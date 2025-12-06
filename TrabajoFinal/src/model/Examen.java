package model;

import java.util.ArrayList;

public class Examen {

    private final int numPreguntas;
    private final ArrayList<Question> preguntas;
    private final long startTime;
    private long endTime;
    private int acertadas;
    private int incorrectas;
    private int noContestadas;

    // -----------------------------
    // Constructor
    // -----------------------------
    public Examen(ArrayList<Question> preguntas, int numPreguntas) {
        this.preguntas = preguntas;
        this.numPreguntas = numPreguntas;
        this.startTime = System.currentTimeMillis();
        this.acertadas = 0;
        this.incorrectas = 0;
        this.noContestadas = 0;
    }

    // -----------------------------
    // Getters
    // -----------------------------
    public int getNumPreguntas() {
        return numPreguntas;
    }

    public ArrayList<Question> getPreguntas() {
        return preguntas;
    }

    public int getAcertadas() {
        return acertadas;
    }

    public int getIncorrectas() {
        return incorrectas;
    }

    public int getNoContestadas() {
        return noContestadas;
    }

    public long getDuracionSegundos() {
        return (endTime - startTime) / 1000;
    }

    // -----------------------------
    // Setters internos (solo modelo)
    // -----------------------------
    public void incrementarAcertadas() {
        acertadas++;
    }

    public void incrementarIncorrectas() {
        incorrectas++;
    }

    public void incrementarNoContestadas() {
        noContestadas++;
    }

    public void finalizarExamen() {
        this.endTime = System.currentTimeMillis();
    }

    // -----------------------------
    // Cálculo de nota
    // -----------------------------
    public double calcularNota() {
        // Cada pregunta vale 10 / numPreguntas
        double valorPregunta = 10.0 / numPreguntas;
        double penalizacion = (valorPregunta / 3.0) * incorrectas;

        double nota = valorPregunta * acertadas - penalizacion;

        if (nota < 0)
            nota = 0; // Nota mínima 0
        return nota;
    }

    // -----------------------------
    // Método resumen
    // -----------------------------
public String getResumen() {
    // Códigos de colores ANSI
    final String RESET = "\u001B[0m";
    final String RED = "\u001B[31m";
    final String GREEN = "\u001B[32m";
    final String YELLOW = "\u001B[33m";
    final String CYAN = "\u001B[36m";
    final String MAGENTA = "\u001B[35m";
    final String WHITE = "\u001B[37m";

    StringBuilder sb = new StringBuilder();
    
    sb.append(CYAN).append("╔══════════════════════════════╗").append(RESET).append("\n");
    sb.append(CYAN).append("║           RESULTADOS         ║").append(RESET).append("\n");
    sb.append(CYAN).append("╠══════════════════════════════╣").append(RESET).append("\n");
    sb.append(WHITE).append(String.format("║ %-20s %4d    ║%n", "Número de preguntas:", numPreguntas));
    sb.append(GREEN).append(String.format("║ %-20s %4d    ║%n", "Acertadas:", acertadas));
    sb.append(RED).append(String.format("║ %-20s %4d    ║%n", "Incorrectas:", incorrectas));
    sb.append(YELLOW).append(String.format("║ %-20s %4d    ║%n", "No contestadas:", noContestadas));
    sb.append(CYAN).append(String.format("║ %-20s %4d s  ║%n", "Tiempo total:", getDuracionSegundos()));
    sb.append(MAGENTA).append(String.format("║ %-20s %4.2f/10 ║%n", "Nota final:", calcularNota()));
    sb.append(CYAN).append("╚══════════════════════════════╝").append(RESET).append("\n");

    return sb.toString();
}


}
