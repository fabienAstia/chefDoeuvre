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

import java.util.*;

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

    public String createAnswersAndGetResult(List<AnswerCreate> inputs) {
        createAnswers(inputs);
        return getMbtiType(inputs);
    }

    private String getMbtiType(List<AnswerCreate> inputs) {
        HashMap<String, List<Integer>> ratingByPsych = getRatingByPsych(inputs);
        HashMap<String, Integer> scoreByPsych = getScoreByPsych(ratingByPsych);
        String userPref1 = (scoreByPsych.get("E") >= scoreByPsych.get("I")) ? "E" : "I";
        String userPref2 = (scoreByPsych.get("N") >= scoreByPsych.get("S")) ? "N" : "S";
        String userPref3 = (scoreByPsych.get("T") >= scoreByPsych.get("F")) ? "T" : "F";
        String userPref4 = (scoreByPsych.get("P") >= scoreByPsych.get("J")) ? "P" : "J";
        return userPref1 + userPref2 + userPref3 + userPref4;
    }

    public HashMap<String, List<Integer>> getRatingByPsych(List<AnswerCreate> answers) {
        HashMap<String, List<Integer>> ratingByPsych = new HashMap<>();
        List<Question> questions = questionRepository.findAll();
        questions.forEach(question -> {
            Optional<AnswerCreate> answer = answers.stream()
                    .filter(a -> a.questionId().equals(question.getId()))
                    .findFirst();
            answer.ifPresent(a -> {
                ratingByPsych.putIfAbsent(question.getPsychPreference().getCode(), new ArrayList<>());
                ratingByPsych.get(question.getPsychPreference().getCode()).add(a.rating());
            });
        });
        return ratingByPsych;
    }

    public HashMap<String, Integer> getScoreByPsych(HashMap<String, List<Integer>> ratingByPsych) {
        HashMap<String, Integer> scoreByPsych = new HashMap<>();
        ratingByPsych.forEach((key, value) -> {
            Integer psychScore = value.stream().mapToInt(i -> i).sum();
            scoreByPsych.put(key, psychScore);
        });
        return scoreByPsych;
    }

    private void createAnswers(List<AnswerCreate> inputs) {
        String username = securityHelper.principal();
        Optional<User> user = userRepository.findByUsernameIgnoreCase(username);
        List<Answer> previousAnswers = answerRepository.findAllByUserId(user.get().getId());
        answerRepository.deleteAll(previousAnswers);
        Set<Answer> answers = newAnswers(inputs, user);
        answerRepository.saveAll(answers);
    }

    private Set<Answer> newAnswers(List<AnswerCreate> inputs, Optional<User> user) {
        Set<Answer> answers = new HashSet<>();
        for (AnswerCreate input : inputs) {
            Answer answer = new Answer();
            Optional<Question> question = questionRepository.findById(input.questionId());
            question.ifPresent(answer::setQuestion);
            user.ifPresent(answer::setUser);
            answer.setRating(input.rating());
            answers.add(answer);
        }
        return answers;
    }
}
