package com.personalities.services;

import com.personalities.config.SecurityHelper;
import com.personalities.dtos.AnswerCreate;
import com.personalities.dtos.MbtiTypeView;
import com.personalities.entities.*;
import com.personalities.repositories.AnswerRepository;
import com.personalities.repositories.MbtiTypeRepository;
import com.personalities.repositories.QuestionRepository;
import com.personalities.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public MbtiTypeView createAnswersAndGetResult(List<AnswerCreate> inputs) {
        createAnswers(inputs);
        String mbtiCode = getMbtiType(inputs);
        MbtiType mbtiType = mbtiTypeRepository.findProjectedByCode(mbtiCode);
        return buildMbtiTypeView(mbtiType, inputs);
    }

    private MbtiTypeView buildMbtiTypeView(MbtiType mbtiType, List<AnswerCreate> inputs) {
        return new MbtiTypeView(
                mbtiType.getCode(),
                mbtiType.getName(),
                mbtiType.getDescription(),
                mbtiType.getPopulationPercentage(),
                mbtiType.getInterestingFact(),
                mbtiType.getImage(),
                mbtiType.getProfessions().stream().map(Profession::getName).toList(),
                buildPersonalityTraitsWithEval(mbtiType),
                getRateByPsych(inputs));
    }

    private Map<String, String> buildPersonalityTraitsWithEval(MbtiType mbtiType) {
        Map<String, String> traitsWithEval = new HashMap<>();
        mbtiType.getPersonalityTraits().forEach(personalityTrait -> {
            traitsWithEval.putIfAbsent(personalityTrait.getTrait(), personalityTrait.getEvaluation().getLabel());
        });
        return traitsWithEval;
    }

    private String getMbtiType(List<AnswerCreate> inputs) {
        Map<String, List<Integer>> ratingByPsych = getRatingByPsych(inputs);
        Map<String, Integer> scoreByPsych = getScoreByPsych(ratingByPsych);
        return getMbtiTypeCode(scoreByPsych);
    }

    private Map<String, Double> getRateByPsych(List<AnswerCreate> inputs) {
        Map<String, List<Integer>> ratingByPsych = getRatingByPsych(inputs);
        Map<String, Integer> scoreByPsych = getScoreByPsych(ratingByPsych);
        Map<String, Double> rateByPsychPref = new HashMap<>();
        scoreByPsych.forEach((key, value) -> rateByPsychPref.putIfAbsent(key, (((double) value / 24) * 100)));
        return rateByPsychPref;
    }

    private static String getMbtiTypeCode(Map<String, Integer> scoreByPsych) {
        return Stream.of(
                        determinePreference(scoreByPsych, PsychPref.EXTRAVERSION, PsychPref.INTROVERSION),
                        determinePreference(scoreByPsych, PsychPref.INTUITION, PsychPref.SENSATION),
                        determinePreference(scoreByPsych, PsychPref.THINKING, PsychPref.FEELING),
                        determinePreference(scoreByPsych, PsychPref.PERCEPTION, PsychPref.JUDGMENT))
                .map(PsychPref::getPrefCode)
                .collect(Collectors.joining());
    }

    private static PsychPref determinePreference(Map<String, Integer> scoreByPsych, PsychPref pref1, PsychPref pref2) {
        return (scoreByPsych.get(pref1.getPrefCode()) >= scoreByPsych.get(pref2.getPrefCode())) ? pref1 : pref2;
    }

    public Map<String, List<Integer>> getRatingByPsych(List<AnswerCreate> answers) {
        Map<String, List<Integer>> ratingByPsych = new HashMap<>();
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

    public Map<String, Integer> getScoreByPsych(Map<String, List<Integer>> ratingByPsych) {
        Map<String, Integer> scoreByPsych = new HashMap<>();
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
