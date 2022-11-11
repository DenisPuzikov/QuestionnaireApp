package ru.puzikov.QuestionnaireApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.puzikov.QuestionnaireApp.models.Questionnaire;
import ru.puzikov.QuestionnaireApp.services.QuestionnairesService;

@Component
public class QuestionnaireValidator implements Validator {

    private final QuestionnairesService questionnairesService;

    @Autowired
    public QuestionnaireValidator(QuestionnairesService questionnairesService) {
        this.questionnairesService = questionnairesService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Questionnaire.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Questionnaire questionnaire = (Questionnaire) target;

        if (questionnairesService.getByHeading(questionnaire.getHeading()).isPresent())
            errors.rejectValue("heading", "",
                    "Опросник с таким названием уже существует");
    }
}
