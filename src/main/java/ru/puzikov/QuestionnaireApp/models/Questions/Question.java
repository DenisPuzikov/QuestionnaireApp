package ru.puzikov.QuestionnaireApp.models.Questions;
import ru.puzikov.QuestionnaireApp.models.Answers.Answer;
import ru.puzikov.QuestionnaireApp.models.Questionnaire;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty
    @NotNull
    @Size(max = 2048)
    @Column(name = "question_text")
    private String questionText;

    @ManyToOne
    @JoinColumn(name = "questionnaire_id", referencedColumnName = "id")
    private Questionnaire owner;

    @OneToMany(mappedBy = "owner")
    private List<Answer> answers;

    public Question(){
    }

    public Question(String questionText, Questionnaire owner) {
        this.questionText = questionText;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Questionnaire getOwner() {
        return owner;
    }

    public void setOwner(Questionnaire owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", owner=" + owner +
                '}';
    }
}
