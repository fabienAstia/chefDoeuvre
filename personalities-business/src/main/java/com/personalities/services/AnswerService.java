package com.personalities.services;

import com.personalities.config.SecurityHelper;
import com.personalities.dtos.AnswerCreate;
import com.personalities.dtos.ResultView;
import com.personalities.entities.*;
import com.personalities.exceptions.QuestionNotFoundException;
import com.personalities.exceptions.UserNotFoundException;
import com.personalities.repositories.AnswerRepository;
import com.personalities.repositories.MbtiTypeRepository;
import com.personalities.repositories.QuestionRepository;
import com.personalities.repositories.UserRepository;
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

    public ResultView createAnswersAndGetResult(List<AnswerCreate> inputs) {
        createAnswers(inputs);
        ResultService resultService = new ResultService(questionRepository, mbtiTypeRepository);
        return resultService.getResult(inputs);
    }

    @Transactional
    private void createAnswers(List<AnswerCreate> inputs) {
        String username = securityHelper.principal();
        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        List<Answer> previousAnswers = answerRepository.findAllByUserId(user.getId());
        answerRepository.deleteAll(previousAnswers);
        Set<Answer> answers = newAnswers(inputs, user);
        answerRepository.saveAll(answers);
    }

    private Set<Answer> newAnswers(List<AnswerCreate> inputs, User user) {
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
