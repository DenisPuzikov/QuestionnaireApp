package ru.puzikov.QuestionnaireApp.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.puzikov.QuestionnaireApp.models.Answers.Answer;
import ru.puzikov.QuestionnaireApp.models.Questionnaire;
import ru.puzikov.QuestionnaireApp.models.Questions.Question;
import ru.puzikov.QuestionnaireApp.repositories.QuestionsRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class QuestionsService {

    private final QuestionsRepository questionsRepository;

    @Autowired
    public QuestionsService(QuestionsRepository questionsRepository) {
        this.questionsRepository = questionsRepository;
    }
    public List<Question> findAll(){
        return questionsRepository.findAll();
    }
    public Question findOne(int id){
        Optional<Question> foundQuestion = questionsRepository.findById(id);
        return foundQuestion.orElse(null);
    }

    @Transactional
    public void save(Question question){
        questionsRepository.save(question);
    }

    @Transactional
    public void delete(int id){
        questionsRepository.deleteById(id);
    }
    @Transactional
    public void update(int id, Question updatedQuestion){
        Question questionToBeUpdated = questionsRepository.findById(id).get();
        updatedQuestion.setId(id);
        updatedQuestion.setOwner(questionToBeUpdated.getOwner());
        questionsRepository.save(updatedQuestion);
    }

    @Transactional
    public void release(int id){
        questionsRepository.findById(id).ifPresent(
                question -> {
                    question.setOwner(null);
                }
        );
    }

    public Questionnaire getQuestionOwner(int id){
        return questionsRepository.findById(id).map(Question::getOwner).orElse(null);
    }

    @Transactional
    public void assign(int id, Questionnaire selectedQuestionnaire){
        questionsRepository.findById(id).ifPresent(
                question ->{
                    question.setOwner(selectedQuestionnaire);
                }
        );
    }


    public Optional<Question> getQuestionByText(String text){
        return questionsRepository.findByQuestionText(text);
    }

    public List<Question> searchByText (String query){
        return questionsRepository.findQuestionByQuestionTextStartingWith(query);
    }

    public List<Answer> getAnswerByQuestionId(int id){
        Optional<Question> question =questionsRepository.findById(id);

        if (question.isPresent()){
            Hibernate.initialize(question.get().getAnswers());
            return question.get().getAnswers();
        } else {
            return Collections.emptyList();
        }
    }
}
