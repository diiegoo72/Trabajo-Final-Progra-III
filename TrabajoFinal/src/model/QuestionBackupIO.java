package model;

import java.util.ArrayList;

public interface QuestionBackupIO {

    public void exportQuestions(ArrayList<Question> questions, String archivo) throws QuestionBackupIOException, RepositoryException;

    public ArrayList<Question> importQuestions(String archivo) throws QuestionBackupIOException, RepositoryException;

    public String getBackupIODescription() throws QuestionBackupIOException;
    
}
