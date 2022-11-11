package ru.puzikov.QuestionnaireApp.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.puzikov.QuestionnaireApp.models.Questionnaire;
import ru.puzikov.QuestionnaireApp.services.QuestionnairesService;
import ru.puzikov.QuestionnaireApp.util.QuestionnaireValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/questionnaires")
public class QuestionnairesController {

    private final QuestionnairesService questionnairesService;

    private final QuestionnaireValidator questionnaireValidator;

    @Autowired
    public QuestionnairesController(QuestionnairesService questionnairesService, QuestionnaireValidator questionnaireValidator) {
        this.questionnairesService = questionnairesService;
        this.questionnaireValidator = questionnaireValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("questionnaires", questionnairesService.findAll());
        return "questionnaires/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("questionnaire", questionnairesService.findOne(id));
        model.addAttribute("questions", questionnairesService.getQuestionsByQuestionnaireId(id));
        return "questionnaires/show";
    }

    @GetMapping("/new")
    public String newQuestionnaire(@ModelAttribute("questionnaire")Questionnaire questionnaire){
        return "questionnaires/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("questionnaire") @Valid Questionnaire questionnaire,
                         BindingResult bindingResult){
        questionnaireValidator.validate(questionnaire, bindingResult);

        if (bindingResult.hasErrors())
            return "questionnaires/new";

        questionnairesService.save(questionnaire);
        return "redirect:/questionnaires";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model,@PathVariable("id") int id) {
        model.addAttribute("questionnaire", questionnairesService.findOne(id));
        return "questionnaires/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("questionnaire") @Valid Questionnaire questionnaire, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "questionnaires/edit";
        questionnairesService.update(id, questionnaire);
        return "redirect:/questionnaires";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        questionnairesService.delete(id);
        return "redirect:/questionnaires";
    }

    @GetMapping("/search")
    public String searchPage(){
        return "questionnaires/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query){
        model.addAttribute("questionnaires", questionnairesService.searchByHeading(query));
        return "questionnaires/search";
    }
}
