package co.simplon.personalities.services;

import co.simplon.personalities.dtos.AnswerCreate;
import co.simplon.personalities.dtos.ResultView;
import co.simplon.personalities.entities.MbtiType;
import co.simplon.personalities.entities.Profession;
import co.simplon.personalities.entities.Question;
import co.simplon.personalities.repositories.MbtiTypeRepository;
import co.simplon.personalities.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ResultService {

    private static int NUMBER_QUESTIONS;
    public static final int MAX_1QUESTION_SCORE = 3;
    private final QuestionRepository questionRepository;
    private final MbtiTypeRepository mbtiTypeRepository;

    @Value("${questions.number}")
    public static void setStaticNumberQuestions(int numberQuestions) {
        NUMBER_QUESTIONS = numberQuestions;
    }

    public static int getStaticNumberQuestions() {
        return NUMBER_QUESTIONS;
    }

    protected ResultService(QuestionRepository questionRepository, MbtiTypeRepository mbtiTypeRepository) {
        this.questionRepository = questionRepository;
        this.mbtiTypeRepository = mbtiTypeRepository;
    }

    public ResultView getResult(List<AnswerCreate> inputs) {
        Map<PsychPref, List<Integer>> ratingByPsych = getRatingByPsych(inputs);
        Map<PsychPref, Integer> scoreByPsych = getScoreByPsych(ratingByPsych);
        Map<PsychPref, Double> percentageByPsych = getPercentageByPsych(scoreByPsych);
        MbtiType mbtiType = getMbtiTypeCode(scoreByPsych);
        return buildResultView(mbtiType, percentageByPsych);
    }

    public ResultView buildResultView(MbtiType mbtiType, Map<PsychPref, Double> percentageByPsych) {
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
        Map<String, String> traitsWithEval = new LinkedHashMap<>();
        mbtiType.getStrengthsAndWeaknesses().forEach(personalityTrait -> {
            traitsWithEval.putIfAbsent(personalityTrait.getTrait(), personalityTrait.getEvaluation().getLabel());
        });
        return traitsWithEval;
    }

    public Map<PsychPref, Double> getPercentageByPsych(Map<PsychPref, Integer> scoreByPsych) {
        Map<PsychPref, Double> percentageByPsychPref = new LinkedHashMap<>();
        Stream.of(PsychPref.values())
                .forEach(psychPref -> {
                    Integer psychPrefScore = scoreByPsych.get(psychPref);
                    Integer complementaryPsychPrefScore = scoreByPsych.get(getComplementaryPsychPref(psychPref));
                    percentageByPsychPref.put(psychPref, getPercentage(psychPrefScore, complementaryPsychPrefScore));
                });
        return percentageByPsychPref;
    }

    private static double getPercentage(Integer psychPrefScore, Integer complementaryPsychPrefScore) {
        double psychologicalPrefScore = psychPrefScore;
        double complementaryPrefScore = complementaryPsychPrefScore;

        int MAX_DIMENSION_SCORE = NUMBER_QUESTIONS / 4 * MAX_1QUESTION_SCORE;
        double USER_DIMENSION_SCORE = psychologicalPrefScore - complementaryPrefScore;

        return (double) Math.round((MAX_DIMENSION_SCORE + USER_DIMENSION_SCORE)
                / (2 * MAX_DIMENSION_SCORE) * 100 * 10)
                / 10;
    }

    public PsychPref getComplementaryPsychPref(PsychPref psychPref) {
        return Stream.of(PsychPref.values())
                .filter(psych -> psych.getCode().equals(psychPref.getComplementary()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Unknown complementaryPsychPref: " + psychPref.getComplementary()));
    }

    public MbtiType getMbtiTypeCode(Map<PsychPref, Integer> scoreByPsych) {
        String mbtiType = Stream.of(
                        determinePreference(scoreByPsych, PsychPref.EXTRAVERSION, PsychPref.INTROVERSION),
                        determinePreference(scoreByPsych, PsychPref.INTUITION, PsychPref.SENSATION),
                        determinePreference(scoreByPsych, PsychPref.THINKING, PsychPref.FEELING),
                        determinePreference(scoreByPsych, PsychPref.PERCEPTION, PsychPref.JUDGMENT))
                .map(PsychPref::getCode)
                .collect(Collectors.joining());
        return mbtiTypeRepository.findProjectedByCode(mbtiType);
    }

    public static PsychPref determinePreference(Map<PsychPref, Integer> scoreByPsych, PsychPref pref1, PsychPref pref2) {
        return (scoreByPsych.get(pref1) >= scoreByPsych.get(pref2)) ? pref1 : pref2;
    }

    public Map<PsychPref, List<Integer>> getRatingByPsych(List<AnswerCreate> answers) {
        Map<PsychPref, List<Integer>> ratingByPsych = new HashMap<>();
        List<Question> questions = questionRepository.findAll();
        questions.forEach(question -> {
            Optional<AnswerCreate> answer = answers.stream()
                    .filter(a -> a.questionId().equals(question.getId()))
                    .findFirst();
            answer.ifPresent(a -> {
                PsychPref psychPref = getPsychPref(question.getPsychPreference().getCode());
                ratingByPsych.putIfAbsent(psychPref, new ArrayList<>());
                ratingByPsych.get(psychPref).add(a.rating());
            });
        });
        return ratingByPsych;
    }

    private static PsychPref getPsychPref(String psychPreferenceCode) {
        return Stream.of(PsychPref.values())
                .filter(psyPref -> psyPref.getCode().equals(psychPreferenceCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown psychPreferenceCode: " + psychPreferenceCode));
    }

    public static Map<PsychPref, Integer> getScoreByPsych(Map<PsychPref, List<Integer>> ratingByPsych) {
        Map<PsychPref, Integer> scoreByPsych = new HashMap<>();
        ratingByPsych.forEach((key, value) -> {
            Integer psychScore = value.stream().mapToInt(i -> i).sum();
            scoreByPsych.put(key, psychScore);
        });
        return scoreByPsych;
    }
}
