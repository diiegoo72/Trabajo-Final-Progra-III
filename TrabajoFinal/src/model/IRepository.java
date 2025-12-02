package model;

import java.util.ArrayList;


public interface IRepository {
    
    public Question addQuestion(Question q); // throws RepositoryException

    public void removeQuestion(Question q); // throws RepositoryException

    public Question modifyQuestion(Question q); // throws RepositoryException
    
    public ArrayList<Question> getAllQuestions(); // throws RepositoryException
    
}