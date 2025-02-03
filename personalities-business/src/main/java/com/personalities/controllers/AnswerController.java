package com.personalities.controllers;

import com.personalities.dto.AnswerCreate;
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
    public String createAnswersAndGetResult(@Valid @RequestBody List<AnswerCreate> inputs) {
        return answerService.createAnswersAndGetResult(inputs);
    }
}

