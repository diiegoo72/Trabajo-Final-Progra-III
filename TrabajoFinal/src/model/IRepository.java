package model;

import java.util.ArrayList;


public interface IRepository {
    
    public Question addQuestion(Question q) throws RepositoryException; // throws RepositoryException

    public void removeQuestion(Question q) throws RepositoryException; // throws RepositoryException

    public void modifyQuestion(Question q) throws RepositoryException; // throws RepositoryException
    
    public ArrayList<Question> getAllQuestions() throws RepositoryException; // throws RepositoryException

    public boolean saveQuestions(ArrayList<Question> questions) throws RepositoryException; // throws RepositoryException
    
}