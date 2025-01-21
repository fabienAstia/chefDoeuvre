package com.personalities.controllers;

import com.personalities.dto.AnswerCreate;
import com.personalities.dto.AnswerUpdate;
import com.personalities.services.AnswerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answers")
@CrossOrigin("*")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAnswers(@Valid @RequestBody List<AnswerCreate> inputs) {
        answerService.createAnswers(inputs);
    }

    
}

