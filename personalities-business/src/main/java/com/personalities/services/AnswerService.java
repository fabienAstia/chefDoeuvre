package com.personalities.services;

import com.personalities.config.SecurityHelper;
import com.personalities.dto.AnswerCreate;
import com.personalities.entities.Answer;
import com.personalities.entities.Question;
import com.personalities.entities.User;
import com.personalities.repositories.AnswerRepository;
import com.personalities.repositories.QuestionRepository;
import com.personalities.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final SecurityHelper securityHelper;
    private final UserRepository userRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository, SecurityHelper securityHelper, UserRepository userRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.securityHelper = securityHelper;
        this.userRepository = userRepository;
    }

    public void createAnswer(AnswerCreate inputs) {
        Answer answer = new Answer();
        Optional<Question> question = questionRepository.findById(inputs.questionId());
        question.ifPresent(answer::setQuestion);
        String username = securityHelper.principal();
        Optional<User> user = userRepository.findByUsernameIgnoreCase(username);
        user.ifPresent(answer::setUser);
        answer.setRating(inputs.rating());
        answerRepository.save(answer);
        System.out.println(answer.toString());
    }
}
