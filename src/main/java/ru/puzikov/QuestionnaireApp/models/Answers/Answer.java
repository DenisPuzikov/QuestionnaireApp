package ru.puzikov.QuestionnaireApp.models.Answers;

import ru.puzikov.QuestionnaireApp.models.Questions.Question;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Size(max = 2048)
    @NotNull(message = "Текст ответа не должен быть пустым")
    @Column(name = "answer_text", length = 2048)
    private String answerText;

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private Question owner;

    @Column(name = "status")
    private int status;

    public Answer(){
    }

    public Answer(String answerText, Question owner, int status) {
        this.answerText = answerText;
        this.owner = owner;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Question getOwner() {
        return owner;
    }

    public void setOwner(Question owner) {
        this.owner = owner;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", answerText='" + answerText + '\'' +
                ", status=" + status +
                '}';
    }
}
