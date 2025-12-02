package model;

import java.util.List;

public interface QuestionBackupIO {

    public void exportQuestions(List<Question> questions);

    public List<Question> importQuestions();

    public String getBackupIODescription();
    
    
}
