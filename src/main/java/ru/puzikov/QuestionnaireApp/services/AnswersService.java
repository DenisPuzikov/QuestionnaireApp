package ru.puzikov.QuestionnaireApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.puzikov.QuestionnaireApp.models.Answers.Answer;
import ru.puzikov.QuestionnaireApp.models.Questions.Question;
import ru.puzikov.QuestionnaireApp.repositories.AnswersRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AnswersService {

    private final AnswersRepository answersRepository;

    @Autowired
    public AnswersService(AnswersRepository answersRepository) {
        this.answersRepository = answersRepository;
    }

    public List<Answer> findAll(boolean sortById){
        if (sortById)
            return answersRepository.findAll(Sort.by("id"));
        else
            return answersRepository.findAll();
    }

    public Answer findOne(int id){
        Optional<Answer> foundAnswer = answersRepository.findById(id);
        return foundAnswer.orElse(null);
    }
    public List<Answer> searchByText(String answerText){
        return answersRepository.findByAnswerTextStartingWith(answerText);
    }

    @Transactional
    public void save(Answer answer){
        answersRepository.save(answer);
    }

    @Transactional
    public void delete(int id){
        answersRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, Answer updatedAnswer){
        Answer answerToBeUpdated = answersRepository.findById(id).get();
        updatedAnswer.setId(id);
        updatedAnswer.setOwner(answerToBeUpdated.getOwner());
        answersRepository.save(updatedAnswer);
    }
    @Transactional
    public void release(int id){
        answersRepository.findById(id).ifPresent(
                answer -> {
                    answer.setOwner(null);
                }
        );
    }
    public Question getAnswerOwner(int id){
        return answersRepository.findById(id).map(Answer::getOwner).orElse(null);
    }
    @Transactional
    public void assign(int id, Question selectedQuestion){
        answersRepository.findById(id).ifPresent(
                answer ->{
                    answer.setOwner(selectedQuestion);
                }
        );
    }
}
