package com.personalities.services;

import com.personalities.dtos.AnswerCreate;
import com.personalities.dtos.ResultView;
import com.personalities.entities.MbtiType;
import com.personalities.entities.Profession;
import com.personalities.entities.Question;
import com.personalities.repositories.MbtiTypeRepository;
import com.personalities.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ResultService {

    public static final int NUMBER_QUESTIONS = 24;
    public static final int MAX_1QUESTION_SCORE = 3;
    private final QuestionRepository questionRepository;
    private final MbtiTypeRepository mbtiTypeRepository;

    protected ResultService(QuestionRepository questionRepository, MbtiTypeRepository mbtiTypeRepository) {
        this.questionRepository = questionRepository;
        this.mbtiTypeRepository = mbtiTypeRepository;
    }

    public ResultView getResult(List<AnswerCreate> inputs) {
        Map<String, List<Integer>> ratingByPsych = getRatingByPsych(inputs);
        Map<String, Integer> scoreByPsych = getScoreByPsych(ratingByPsych);
        String mbtiCode = getMbtiTypeCode(scoreByPsych);
        Map<String, Double> percentageByPsych = getPercentageByPsych(scoreByPsych);
        MbtiType mbtiType = mbtiTypeRepository.findProjectedByCode(mbtiCode);
        return buildMbtiTypeView(mbtiType, percentageByPsych);
    }

    public ResultView buildMbtiTypeView(MbtiType mbtiType, Map<String, Double> percentageByPsych) {
        return new ResultView(
                mbtiType.getCode(),
                mbtiType.getName(),
                mbtiType.getDescription(),
                mbtiType.getPopulationPercentage(),
                mbtiType.getInterestingFact(),
                mbtiType.getImage(),
                mbtiType.getProfessions().stream().map(Profession::getName).toList(),
                buildStrengthAndWeaknesses(mbtiType),
                percentageByPsych);
    }

    public Map<String, String> buildStrengthAndWeaknesses(MbtiType mbtiType) {
        Map<String, String> traitsWithEval = new HashMap<>();
        mbtiType.getStrengthAndWeaknesses().forEach(personalityTrait -> {
            traitsWithEval.putIfAbsent(personalityTrait.getTrait(), personalityTrait.getEvaluation().getLabel());
        });
        return traitsWithEval;
    }

    public Map<String, Double> getPercentageByPsych(Map<String, Integer> scoreByPsych) {
        Map<String, Double> percentageByPsychPref = new LinkedHashMap<>();
        Stream.of(PsychPref.values())
                .forEach(psychPref -> {
                    Map.Entry<String, Integer> psychPrefPercentage = scoreByPsych.entrySet().stream()
                            .filter(entry -> entry.getKey().equals(psychPref.getCode()))
                            .toList().getFirst();
                    Map.Entry<String, Integer> complementary = scoreByPsych.entrySet().stream()
                            .filter(entry -> entry.getKey().equals(getComplementaryPsychPref(psychPref).getCode()))
                            .toList().getFirst();
                    percentageByPsychPref.put(psychPref.getCode(), getPercentage(psychPrefPercentage, complementary));
                });
        return percentageByPsychPref;
    }

    private static double getPercentage(Map.Entry<String, Integer> psychologicalPreference, Map.Entry<String, Integer> complementaryPreference) {
        double psychologicalPref = psychologicalPreference.getValue();
        double complementaryPref = complementaryPreference.getValue();

        int MAX_DIMENSION_SCORE = NUMBER_QUESTIONS / 4 * MAX_1QUESTION_SCORE;
        double USER_DIMENSION_SCORE = psychologicalPref - complementaryPref;

        return (double) Math.round((MAX_DIMENSION_SCORE + USER_DIMENSION_SCORE)
                / (2 * MAX_DIMENSION_SCORE) * 100 * 10)
                / 10;
    }

    public PsychPref getComplementaryPsychPref(PsychPref psychPref) {
        return Stream.of(PsychPref.values())
                .filter(psych -> psych.getCode().equals(psychPref.getComplementary()))
                .toList().getFirst();
    }

    public static String getMbtiTypeCode(Map<String, Integer> scoreByPsych) {
        return Stream.of(
                        determinePreference(scoreByPsych, PsychPref.EXTRAVERSION, PsychPref.INTROVERSION),
                        determinePreference(scoreByPsych, PsychPref.INTUITION, PsychPref.SENSATION),
                        determinePreference(scoreByPsych, PsychPref.THINKING, PsychPref.FEELING),
                        determinePreference(scoreByPsych, PsychPref.PERCEPTION, PsychPref.JUDGMENT))
                .map(PsychPref::getCode)
                .collect(Collectors.joining());
    }

    public static PsychPref determinePreference(Map<String, Integer> scoreByPsych, PsychPref pref1, PsychPref pref2) {
        return (scoreByPsych.get(pref1.getCode()) >= scoreByPsych.get(pref2.getCode())) ? pref1 : pref2;
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

    public static Map<String, Integer> getScoreByPsych(Map<String, List<Integer>> ratingByPsych) {
        Map<String, Integer> scoreByPsych = new HashMap<>();
        ratingByPsych.forEach((key, value) -> {
            Integer psychScore = value.stream().mapToInt(i -> i).sum();
            scoreByPsych.put(key, psychScore);
        });
        return scoreByPsych;
    }
}
