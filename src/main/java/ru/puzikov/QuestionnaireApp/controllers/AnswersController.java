package ru.puzikov.QuestionnaireApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.puzikov.QuestionnaireApp.models.Answers.Answer;
import ru.puzikov.QuestionnaireApp.models.Questions.Question;
import ru.puzikov.QuestionnaireApp.services.AnswersService;
import ru.puzikov.QuestionnaireApp.services.QuestionsService;

import javax.validation.Valid;

@Controller
@RequestMapping("/answers")
public class AnswersController {

    private final QuestionsService questionsService;
    private final AnswersService answersService;

    @Autowired
    public AnswersController(QuestionsService questionsService, AnswersService answersService) {
        this.questionsService = questionsService;
        this.answersService = answersService;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(value = "sort_by_id", required = false) boolean sortById){
        model.addAttribute("answers", answersService.findAll(sortById));
        return "answers/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("question") Question owner){
        model.addAttribute("answer", answersService.findOne(id));

        Question answerOwner = answersService.getAnswerOwner(id);

        if (answerOwner != null)
            model.addAttribute("owner", answerOwner);
        else
            model.addAttribute("questions", questionsService.findAll());

        return "answers/show";
    }

    @GetMapping("/new")
    public String newAnswer(@ModelAttribute("answer")Answer answer){
        return "answers/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("answer") @Valid Answer answer,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "answers/new";

        answersService.save(answer);
        return "redirect:/answers";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("answer", answersService.findOne(id));
        return "answers/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("answer") @Valid Answer answer, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "answers/edit";

        answersService.update(id, answer);
        return "redirect:/answers";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        answersService.delete(id);
        return "redirect:/answers";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        answersService.release(id);
        return "redirect:/answers/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("question") Question selectedQuestion) {
        answersService.assign(id, selectedQuestion);
        return "redirect:/answers/" + id;
    }

    @GetMapping("/search")
    public String searchPage(){
        return "answers/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("answerText") String answerText){
        model.addAttribute("answers", answersService.searchByText(answerText));
        return "answers/search";
    }
}
