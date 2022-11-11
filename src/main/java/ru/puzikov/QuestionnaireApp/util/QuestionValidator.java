package ru.puzikov.QuestionnaireApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.puzikov.QuestionnaireApp.models.Questions.Question;
import ru.puzikov.QuestionnaireApp.services.QuestionsService;

@Component
public class QuestionValidator implements Validator {

    private final QuestionsService questionsService;

    @Autowired
    public QuestionValidator(QuestionsService questionsService) {
        this.questionsService = questionsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Question.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Question question = (Question) target;

        if (questionsService.getQuestionByText(question.getQuestionText()).isPresent())
            errors.rejectValue("questionText", "",
                    "Вопрос с такой формулировкой уже существует");
    }
}
