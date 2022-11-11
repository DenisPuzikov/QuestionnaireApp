package ru.puzikov.QuestionnaireApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.puzikov.QuestionnaireApp.models.Questions.Question;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionsRepository extends JpaRepository<Question, Integer> {

    Optional<Question> findByQuestionText(String text);

    List<Question> findQuestionByQuestionTextStartingWith (String query);
}
