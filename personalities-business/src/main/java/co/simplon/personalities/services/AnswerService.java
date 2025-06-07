package co.simplon.personalities.services;

import co.simplon.personalities.dtos.AnswerCreate;
import co.simplon.personalities.entities.Answer;
import co.simplon.personalities.entities.Question;
import co.simplon.personalities.entities.User;
import co.simplon.personalities.exceptions.QuestionNotFoundException;
import co.simplon.personalities.exceptions.UserNotFoundException;
import co.simplon.personalities.repositories.AnswerRepository;
import co.simplon.personalities.repositories.UserRepository;
import co.simplon.personalities.config.SecurityHelper;
import co.simplon.personalities.dtos.ResultView;
import co.simplon.personalities.entities.*;
import co.simplon.personalities.repositories.MbtiTypeRepository;
import co.simplon.personalities.repositories.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final SecurityHelper securityHelper;
    private final UserRepository userRepository;
    private final MbtiTypeRepository mbtiTypeRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository, SecurityHelper securityHelper, UserRepository userRepository, MbtiTypeRepository mbtiTypeRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.securityHelper = securityHelper;
        this.userRepository = userRepository;
        this.mbtiTypeRepository = mbtiTypeRepository;
    }

    public ResultView submitAnswersAndGetResult(List<AnswerCreate> inputs) {
        submitAnswers(inputs);
        ResultService resultService = new ResultService(questionRepository, mbtiTypeRepository);
        return resultService.getResult(inputs);
    }

    @Transactional
    private void submitAnswers(List<AnswerCreate> inputs) {
        String username = securityHelper.principal();
        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        List<Answer> previousAnswers = answerRepository.findAllByUserId(user.getId());
        answerRepository.deleteAll(previousAnswers);
        Set<Answer> answers = createNewAnswers(inputs, user);
        answerRepository.saveAll(answers);
    }

    private Set<Answer> createNewAnswers(List<AnswerCreate> inputs, User user) {
        Set<Answer> answers = new HashSet<>();
        for (AnswerCreate input : inputs) {
            Answer answer = new Answer();
            Question question = questionRepository.findById(input.questionId())
                    .orElseThrow(() -> new QuestionNotFoundException(input.questionId()));
            answer.setQuestion(question);
            answer.setUser(user);
            answer.setRating(input.rating());
            answers.add(answer);
        }
        return answers;
    }
}
