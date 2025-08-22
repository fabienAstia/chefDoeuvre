package co.simplon.personalities.services;

import co.simplon.personalities.dtos.AnswerCreate;
import co.simplon.personalities.dtos.AnswerCreateList;
import co.simplon.personalities.entities.Answer;
import co.simplon.personalities.entities.Question;
import co.simplon.personalities.entities.User;
import co.simplon.personalities.exceptions.QuestionNotFoundException;
import co.simplon.personalities.exceptions.UserNotFoundException;
import co.simplon.personalities.repositories.AnswerRepository;
import co.simplon.personalities.repositories.UserRepository;
import co.simplon.personalities.config.SecurityHelper;
import co.simplon.personalities.dtos.ResultView;
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
    private final ResultService resultService;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository, SecurityHelper securityHelper, UserRepository userRepository, MbtiTypeRepository mbtiTypeRepository, ResultService resultService) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.securityHelper = securityHelper;
        this.userRepository = userRepository;
        this.mbtiTypeRepository = mbtiTypeRepository;
        this.resultService = resultService;
    }

    @Transactional
    public ResultView submitAnswersAndGetResult(AnswerCreateList inputs) {
        List<AnswerCreate> answers = inputs.answers();
        User user = getUserAndSubmitAnswers(answers);
        ResultView resultView = resultService.getResult(answers);
        user.setMbtiType(mbtiTypeRepository.findProjectedByCode(resultView.getCode()));
        userRepository.save(user);
        return resultView;
    }

    private User getUserAndSubmitAnswers(List<AnswerCreate> inputs) {
        String username = securityHelper.principal();
        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        answerRepository.deleteAllByUserId(user.getId());
        Set<Answer> answers = createNewAnswers(inputs, user);
        answerRepository.saveAll(answers);
        return user;
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
