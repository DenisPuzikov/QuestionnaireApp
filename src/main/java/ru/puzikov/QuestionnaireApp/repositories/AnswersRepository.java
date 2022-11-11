package ru.puzikov.QuestionnaireApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.puzikov.QuestionnaireApp.models.Answers.Answer;

import java.util.List;

@Repository
public interface AnswersRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findByAnswerTextStartingWith(String answerText);
}
