package com.personalities.controllers;

import com.personalities.dto.QuestionCreate;
import com.personalities.dto.QuestionView;
import com.personalities.services.PersonalitiesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personalities")
@CrossOrigin("*")
public class PersonalitiesController {

    private final PersonalitiesService service;

    public PersonalitiesController(PersonalitiesService service) {
        this.service = service;
    }

    @PostMapping("/question")
    public void createQuestion(@RequestBody QuestionCreate question){
        service.create(question);
    }

    @GetMapping
    public List<QuestionView> getQuestions (){
        return service.getQuestions();
    }
    @DeleteMapping("/{id}") // supprimer une question, indiquer son id
    public void deleteQuestion(@PathVariable ("id") Long id){
        service.deleteQuestion(id);
    }
}
