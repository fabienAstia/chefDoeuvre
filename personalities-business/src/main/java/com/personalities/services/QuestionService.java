package com.personalities.services;

import com.personalities.dto.QuestionCreate;
import com.personalities.dto.QuestionUpdate;
import com.personalities.dto.QuestionView;
import com.personalities.entities.Question;
import com.personalities.entities.Dimension;
import com.personalities.repositories.DimensionRepository;
import com.personalities.repositories.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final DimensionRepository dimensionRepository;

    public QuestionService(QuestionRepository questionRepository, DimensionRepository dimensionRepository) {
        this.questionRepository = questionRepository;
        this.dimensionRepository = dimensionRepository;
    }

    public ResponseEntity<Object> createQuestion(QuestionCreate questionCreate) {
        Question question = new Question();
        Dimension dimension = dimensionRepository.findByNameIgnoreCase(questionCreate.dimension());
        question.setText(questionCreate.text());
        question.setDimension(dimension);
        questionRepository.save(question);
        return null;
    }

    public Set<QuestionView> getQuestions() {
        List<Question> questionList = questionRepository.findAllProjectedBy();
        return questionList.stream().map(question -> new QuestionView(
                        question.getId(), question.getText(), question.getDimension().getName()))
                .collect(Collectors.toSet());
    }

    public Page<Question> getPaginatedQuestions(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public void updateQuestion(Long id, QuestionUpdate inputs) {
        Question question = questionRepository.findById(id).get();
        Dimension dimension = dimensionRepository.findByNameIgnoreCase(inputs.dimension());
        question.setText(inputs.text());
        question.setDimension(dimension);
        questionRepository.save(question);
    }

}
