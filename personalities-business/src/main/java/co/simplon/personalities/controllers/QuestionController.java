package co.simplon.personalities.controllers;

import co.simplon.personalities.dtos.QuestionCreate;
import co.simplon.personalities.dtos.QuestionUpdate;
import co.simplon.personalities.services.QuestionService;
import co.simplon.personalities.dtos.AdminQuestionView;
import co.simplon.personalities.dtos.UserQuestionView;
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
    public void createQuestion(@Valid @RequestBody QuestionCreate question) {
        service.createQuestion(question);
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
        return service.getPaginatedQuestions(page, size);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable("id") Long id) {
        service.deleteQuestion(id);
    }

}
