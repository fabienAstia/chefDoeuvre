package com.personalities.controllers;

import com.personalities.dto.QuestionCreate;
import com.personalities.dto.QuestionUpdate;
import com.personalities.dto.QuestionView;
import com.personalities.services.QuestionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService service;

    public QuestionController(QuestionService service) {
        this.service = service;
    }

    @PostMapping
    public void createQuestion(@Valid @RequestBody QuestionCreate question) {
        service.createQuestion(question);
    }

    @PutMapping("/{id}")
    public void updateQuestion(@PathVariable("id") Long id,
                               @Valid @RequestBody QuestionUpdate inputs) {
        service.updateQuestion(id, inputs);
    }

    @GetMapping
    public Set<QuestionView> getQuestions() {
        return service.getQuestions();
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable("id") Long id) {
        service.deleteQuestion(id);
    }

}
