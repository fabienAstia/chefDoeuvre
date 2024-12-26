package com.personalities.controllers;

import com.personalities.dto.AnswerCreate;
import com.personalities.services.AnswerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answers")
@CrossOrigin("*")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public void createAnswer(@Valid @RequestBody AnswerCreate inputs) {
        answerService.createAnswer(inputs);
    }
}

