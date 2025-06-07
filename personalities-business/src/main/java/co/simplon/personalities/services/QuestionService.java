package co.simplon.personalities.services;

import co.simplon.personalities.dtos.QuestionCreate;
import co.simplon.personalities.dtos.QuestionUpdate;
import co.simplon.personalities.entities.Constraint;
import co.simplon.personalities.repositories.ConstraintRepository;
import co.simplon.personalities.repositories.PsychPreferenceRepository;
import co.simplon.personalities.dtos.AdminQuestionView;
import co.simplon.personalities.dtos.UserQuestionView;
import co.simplon.personalities.entities.Question;
import co.simplon.personalities.entities.PsychPreference;
import co.simplon.personalities.repositories.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public Set<AdminQuestionView> getQuestions() {
        List<Question> questionList = questionRepository.findAllProjectedBy();
        return questionList.stream().map(question -> new AdminQuestionView(
                        question.getId(), question.getLabel(), question.getPsychPreference().getCode()))
                .collect(Collectors.toSet());
    }

    public Page<UserQuestionView> getPaginatedQuestions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Question> questions = questionRepository.findAllByOrderByOrder(pageable);
        return questions.map(question -> new UserQuestionView(
                question.getId(),
                question.getLabel(),
                question.getOrder(),
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
