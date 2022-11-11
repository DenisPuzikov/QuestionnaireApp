package ru.puzikov.QuestionnaireApp.models;

import org.hibernate.annotations.SortNatural;
import ru.puzikov.QuestionnaireApp.models.Answers.Answer;
import ru.puzikov.QuestionnaireApp.models.Questions.Question;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
Сущность опросник с вопросами и списком ответов
 */

@Entity
@Table(name = "questionnaire")
public class Questionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Size(max = 100)
    @Column(name = "heading")
    private String heading;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Question> questions;

    public Questionnaire(){
    }

    public Questionnaire(String heading) {
        this.heading = heading;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Questionnaire{" +
                "id=" + id +
                ", heading='" + heading + '\'' +
                '}';
    }
}
