package ru.puzikov.QuestionnaireApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.puzikov.QuestionnaireApp.models.Questionnaire;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionnairesRepository extends JpaRepository<Questionnaire, Integer> {
    Optional<Questionnaire> findByHeading(String heading);
    List<Questionnaire> findByHeadingStartingWith(String query);
}
