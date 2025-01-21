package com.personalities.services;

import com.personalities.dto.QuestionCreate;
import com.personalities.dto.QuestionUpdate;
import com.personalities.dto.QuestionView;
import com.personalities.entities.Constraint;
import com.personalities.entities.Question;
import com.personalities.entities.PsychPreference;
import com.personalities.repositories.ConstraintRepository;
import com.personalities.repositories.PsychPreferenceRepository;
import com.personalities.repositories.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final PsychPreferenceRepository psychPrefRepository;
    private final ConstraintRepository constraintRepository;

    public QuestionService(QuestionRepository questionRepository, PsychPreferenceRepository psychPrefRepository, ConstraintRepository constraintRepository) {
        this.questionRepository = questionRepository;
        this.psychPrefRepository = psychPrefRepository;
        this.constraintRepository = constraintRepository;
    }

    public ResponseEntity<Object> createQuestion(QuestionCreate questionCreate) {
        PsychPreference psychPreference = psychPrefRepository.findByCodeIgnoreCase(questionCreate.psychPref());
        Constraint constraint = constraintRepository.findByMinAndMax(-3, 3);
        Question question = new Question();
        question.setLabel(questionCreate.label());
        question.setConstraint(constraint);
        question.setPsychPreference(psychPreference);
        questionRepository.save(question);
        return null;
    }

    public Set<QuestionView> getQuestions() {
        List<Question> questionList = questionRepository.findAllProjectedBy();
        return questionList.stream().map(question -> new QuestionView(
                        question.getId(), question.getLabel(), question.getPsychPreference().getCode()))
                .collect(Collectors.toSet());
    }

//    public Page<Question> getPaginatedQuestions(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return questionRepository.findAll(pageable);
//    }

    public Page<QuestionView> getPaginatedQuestions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Question> questions = questionRepository.findAll(pageable);
        return questions.map(question -> new QuestionView(
                question.getId(),
                question.getLabel(),
                question.getPsychPreference().getCode()));
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public void updateQuestion(Long id, QuestionUpdate inputs) {
        Question question = questionRepository.findById(id).get();
        PsychPreference psychPreference = psychPrefRepository.findByCodeIgnoreCase(inputs.psychPref());
        question.setLabel(inputs.label());
        question.setPsychPreference(psychPreference);
        questionRepository.save(question);
    }

}
