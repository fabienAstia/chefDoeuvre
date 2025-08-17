package co.simplon.personalities.controllers;

import co.simplon.personalities.dtos.AnswerCreateList;
import co.simplon.personalities.services.AnswerService;
import co.simplon.personalities.dtos.ResultView;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultView submitAnswersAndGetResult(@Valid @RequestBody AnswerCreateList inputs) {
        return answerService.submitAnswersAndGetResult(inputs);
    }
}

