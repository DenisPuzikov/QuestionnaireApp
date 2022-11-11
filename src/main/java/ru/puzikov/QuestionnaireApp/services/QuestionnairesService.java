package ru.puzikov.QuestionnaireApp.services;


import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.puzikov.QuestionnaireApp.models.Questionnaire;
import ru.puzikov.QuestionnaireApp.models.Questions.Question;
import ru.puzikov.QuestionnaireApp.repositories.QuestionnairesRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class QuestionnairesService {
    private final QuestionnairesRepository questionnairesRepository;

    @Autowired
    public QuestionnairesService(QuestionnairesRepository questionnaireRepository) {
        this.questionnairesRepository = questionnaireRepository;
    }

    public List<Questionnaire> findAll(){
        return questionnairesRepository.findAll();
    }

    public Questionnaire findOne(int id){
        Optional<Questionnaire> foundQuestionnaire = questionnairesRepository.findById(id);
        return foundQuestionnaire.orElse(null);
    }

    @Transactional
    public void save(Questionnaire questionnaire){
        questionnairesRepository.save(questionnaire);
    }

    @Transactional
    public void update(int id, Questionnaire updatedQuestionnaire){
        updatedQuestionnaire.setId(id);
        questionnairesRepository.save(updatedQuestionnaire);
    }

    @Transactional
    public void delete(int id){
        questionnairesRepository.deleteById(id);
    }

    public Optional<Questionnaire> getByHeading(String heading){
        return questionnairesRepository.findByHeading(heading);
    }

    public List<Questionnaire> searchByHeading (String query){
        return questionnairesRepository.findByHeadingStartingWith(query);
    }

    public List<Question> getQuestionsByQuestionnaireId(int id){
        Optional<Questionnaire> questionnaire = questionnairesRepository.findById(id);

        if (questionnaire.isPresent()){
            Hibernate.initialize(questionnaire.get().getQuestions());
            return questionnaire.get().getQuestions();
        } else {
            return Collections.emptyList();
        }
    }









}
