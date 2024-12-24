package com.personalities.services;

import com.personalities.dto.AnswerCreate;
import com.personalities.entities.Answer;
import com.personalities.entities.Question;
import com.personalities.entities.User;
import com.personalities.repositories.AnswerRepository;
import com.personalities.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    public void createAnswer(AnswerCreate inputs) {
        Optional<Question> question = questionRepository.findById(inputs.questionId());
        if (question.isPresent()) {
            Answer answer = new Answer();
            User user = new User();
            answer.setQuestion(question.get());
            answer.setUser(user);
            answer.setRating(inputs.rating());
            //userRepository.save(user);
            answerRepository.save(answer);
        }
    }
}
