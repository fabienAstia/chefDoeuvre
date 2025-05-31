package com.personalities.controllers;

import com.personalities.dtos.AnswerCreate;
import com.personalities.dtos.ResultView;
import com.personalities.services.AnswerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultView submitAnswersAndGetResult(@Valid @RequestBody List<AnswerCreate> inputs) {
//        return answerService.submitAnswersAndGetResult(inputs);
        ResultView m = answerService.submitAnswersAndGetResult(inputs);
        System.out.println("mbtiType" + answerService.submitAnswersAndGetResult(inputs).toString());
        return m;
    }
}

