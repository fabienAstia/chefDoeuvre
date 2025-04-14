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
    public ResultView createAnswersAndGetResult(@Valid @RequestBody List<AnswerCreate> inputs) {
//        return answerService.createAnswersAndGetResult(inputs);
        ResultView m = answerService.createAnswersAndGetResult(inputs);
        System.out.println("mbtiType" + answerService.createAnswersAndGetResult(inputs).toString());
        return m;
    }
}

