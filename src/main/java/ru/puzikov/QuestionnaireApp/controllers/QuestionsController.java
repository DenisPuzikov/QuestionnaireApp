package ru.puzikov.QuestionnaireApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.puzikov.QuestionnaireApp.models.Questionnaire;
import ru.puzikov.QuestionnaireApp.models.Questions.Question;
import ru.puzikov.QuestionnaireApp.services.QuestionnairesService;
import ru.puzikov.QuestionnaireApp.services.QuestionsService;
import ru.puzikov.QuestionnaireApp.util.QuestionValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/questions")
public class QuestionsController {

    private final QuestionnairesService questionnairesService;
    private final QuestionsService questionsService;
    private final QuestionValidator questionValidator;

    @Autowired
    public QuestionsController(QuestionnairesService questionnairesService, QuestionsService questionsService, QuestionValidator questionValidator) {
        this.questionnairesService = questionnairesService;
        this.questionsService = questionsService;
        this.questionValidator = questionValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("questions", questionsService.findAll());
        return "questions/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("questionnaire") Questionnaire owner){

        model.addAttribute("question", questionsService.findOne(id));
        model.addAttribute("answers", questionsService.getAnswerByQuestionId(id));

        Questionnaire questionOwner = questionsService.getQuestionOwner(id);

        if (questionOwner != null)
            model.addAttribute("owner", questionOwner);
        else
            model.addAttribute("questionnaires", questionnairesService.findAll());

        return "questions/show";
    }

    @GetMapping("/new")
    public String newQuestion(@ModelAttribute("question") Question question){
        return "questions/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("question") @Valid Question question,
                         BindingResult bindingResult){
        questionValidator.validate(question, bindingResult);

        if (bindingResult.hasErrors())
            return "questions/new";

        questionsService.save(question);
        return "redirect:/questions";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model,@PathVariable("id") int id) {
        model.addAttribute("question", questionsService.findOne(id));
        return "questions/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("question") @Valid Question question, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "questions/edit";
        questionsService.update(id, question);
        return "redirect:/questions";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        questionsService.delete(id);
        return "redirect:/questions";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        questionsService.release(id);
        return "redirect:/questions/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("questionnaire") Questionnaire selectedQuestionnaire) {
        questionsService.assign(id, selectedQuestionnaire);
        return "redirect:/questions/" + id;
    }

    @GetMapping("/search")
    public String searchPage(){
        return "questions/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query){
        model.addAttribute("questions", questionsService.searchByText(query));
        return "questions/search";
    }
}
