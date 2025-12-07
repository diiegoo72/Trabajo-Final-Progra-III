package model;

import java.util.ArrayList;


public interface IRepository {
    
    public void addQuestion(Question q) throws RepositoryException;

    public void removeQuestion(Question q) throws RepositoryException;

    public void modifyQuestion(Question q) throws RepositoryException;
    
    public ArrayList<Question> getAllQuestions() throws RepositoryException;

    public void saveQuestions() throws RepositoryException;

    public ArrayList<Question> getAllQuestionsFromRepository() throws RepositoryException;
    
}