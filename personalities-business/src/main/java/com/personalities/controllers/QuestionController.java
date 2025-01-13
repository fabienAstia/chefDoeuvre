package com.personalities.controllers;

import com.personalities.dto.QuestionCreate;
import com.personalities.dto.QuestionUpdate;
import com.personalities.dto.QuestionView;
import com.personalities.entities.Question;
import com.personalities.services.QuestionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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
    public Set<QuestionView> getQuestions() {
        return service.getQuestions();
    }

    @GetMapping("/paginated")
    public Page<Question> getPaginatedQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getPaginatedQuestions(pageable);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable("id") Long id) {
        service.deleteQuestion(id);
    }

}
