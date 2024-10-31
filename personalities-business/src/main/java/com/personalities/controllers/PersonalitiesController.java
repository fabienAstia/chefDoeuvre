package com.personalities.controllers;

import com.personalities.dto.QuestionCreate;
import com.personalities.dto.QuestionUpdate;
import com.personalities.dto.QuestionView;
import com.personalities.services.PersonalitiesService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/questions")
@CrossOrigin("*")
public class PersonalitiesController {

    private final PersonalitiesService service;

    public PersonalitiesController(PersonalitiesService service) {
        this.service = service;
    }

    @PostMapping
    public void createQuestion(@Valid @RequestBody QuestionCreate question) {
        service.create(question);
    }

    @PutMapping("/{id}")
    public void updateQuestion(@PathVariable ("id") Long id,
                               @Valid @RequestBody QuestionUpdate inputs) throws Exception{
        service.updateQuestion(id, inputs);
    }

    @GetMapping
    public List<QuestionView> getQuestions (){
        return service.getQuestions();
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable ("id") Long id){
        service.deleteQuestion(id);
    }

}
