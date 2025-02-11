package com.personalities.controllers;

import com.personalities.dto.AdminQuestionView;
import com.personalities.dto.QuestionCreate;
import com.personalities.dto.QuestionUpdate;
import com.personalities.dto.UserQuestionView;
import com.personalities.services.QuestionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createQuestion(@Valid @RequestBody QuestionCreate question) {
        return service.createQuestion(question);
    }

    @PutMapping("/{id}")
    public void updateQuestion(@PathVariable("id") Long id,
                               @Valid @RequestBody QuestionUpdate inputs) {
        service.updateQuestion(id, inputs);
    }

    @GetMapping
    public Set<AdminQuestionView> getQuestions() {
        return service.getQuestions();
    }

    @GetMapping("/paginated")
    public Page<UserQuestionView> getPaginatedQuestions(
            @RequestParam(value = "pageNum", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "8") int size
    ) {
        System.out.println("pages = " + service.getPaginatedQuestions(page, size).getContent());
        return service.getPaginatedQuestions(page, size);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable("id") Long id) {
        service.deleteQuestion(id);
    }

}
