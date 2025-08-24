package co.simplon.personalities.services;

import co.simplon.personalities.entities.MbtiType;
import co.simplon.personalities.repositories.MbtiTypeRepository;
import co.simplon.personalities.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResultServiceTest {

    @Mock
    QuestionRepository questionRepository;
    @Mock
    MbtiTypeRepository mbtiTypeRepository;
    private ResultService resultService;

    @BeforeEach
    void setUp() {
        resultService = new ResultService(8, questionRepository, mbtiTypeRepository);
    }

    @ParameterizedTest
    @MethodSource("scoresProvider")
    void getMbtiTypeCode_OK(Map<PsychPref, Integer> scoreByPsych, String expectedCode) {

        MbtiType mbtiType = new MbtiType();
        when(mbtiTypeRepository.findProjectedByCode(expectedCode)).thenReturn(mbtiType);

        var result = resultService.getMbtiTypeCode(scoreByPsych);

        verify(mbtiTypeRepository).findProjectedByCode(expectedCode);
        assertSame(mbtiType, result);
    }

    @ParameterizedTest
    @MethodSource("percentagesProvider")
    void getPercentageByPsych_OK(Map<PsychPref, Integer> scoreByPsych, Map<PsychPref, Double> expectedPercentages) {

        var result = resultService.getPercentageByPsych(scoreByPsych);

        for (PsychPref p : PsychPref.values()) {
            assertEquals(expectedPercentages.get(p), result.get(p));
        }
    }

    static Stream<Arguments> scoresProvider() {
        return Stream.of(
                Arguments.of(scoreByPsych_ENTP(), "ENTP"),
                Arguments.of(scoreByPsych_ESFJ(), "ESFJ"),
                Arguments.of(scoreByPsych_INFP(), "INFP"),
                Arguments.of(scoreByPsych_ISTJ(), "ISTJ"),
                Arguments.of(scoreByPsych_ESTP(), "ESTP")
        );
    }

    // (numberQuestions/4) * 3 = 6
    // %Pôle = 50 + (scorePôle - scoreOpposé) * (50.0 / 6);
    static Stream<Arguments> percentagesProvider() {
        return Stream.of(
                Arguments.of(scoreByPsych_ENTP(), percentageByPsych_ENTP()),
                Arguments.of(scoreByPsych_ESFJ(), percentageByPsych_ESFJ()),
                Arguments.of(scoreByPsych_INFP(), percentageByPsych_INFP()),
                Arguments.of(scoreByPsych_ISTJ(), percentageByPsych_ISTJ()),
                Arguments.of(scoreByPsych_ESTP(), percentageByPsych_ESTP())
        );
    }

    //50%/50%
    static Map<PsychPref, Integer> scoreByPsych_ENTP() {
        return Map.of(
                PsychPref.EXTRAVERSION, 0,
                PsychPref.INTROVERSION, 0,
                PsychPref.INTUITION, 0,
                PsychPref.SENSATION, 0,
                PsychPref.THINKING, 0,
                PsychPref.FEELING, 0,
                PsychPref.PERCEPTION, 0,
                PsychPref.JUDGMENT, 0
        );
    }

    // 100%/0%
    static Map<PsychPref, Integer> scoreByPsych_ESFJ() {
        return Map.of(
                PsychPref.EXTRAVERSION, 3,
                PsychPref.INTROVERSION, -3,
                PsychPref.INTUITION, -3,
                PsychPref.SENSATION, 3,
                PsychPref.THINKING, -3,
                PsychPref.FEELING, 3,
                PsychPref.PERCEPTION, -3,
                PsychPref.JUDGMENT, 3
        );
    }

    //75%/25%
    static Map<PsychPref, Integer> scoreByPsych_INFP() {
        return Map.of(
                PsychPref.EXTRAVERSION, 0,
                PsychPref.INTROVERSION, 3,
                PsychPref.INTUITION, 3,
                PsychPref.SENSATION, 0,
                PsychPref.THINKING, -3,
                PsychPref.FEELING, 0,
                PsychPref.PERCEPTION, 0,
                PsychPref.JUDGMENT, -3
        );
    }

    //83.3%/16.7%
    static Map<PsychPref, Integer> scoreByPsych_ISTJ() {
        return Map.of(
                PsychPref.EXTRAVERSION, -2,
                PsychPref.INTROVERSION, 2,
                PsychPref.INTUITION, -1,
                PsychPref.SENSATION, 3,
                PsychPref.THINKING, 1,
                PsychPref.FEELING, -3,
                PsychPref.PERCEPTION, -2,
                PsychPref.JUDGMENT, 2
        );
    }

    //58.3%/41.7%
    static Map<PsychPref, Integer> scoreByPsych_ESTP() {
        return Map.of(
                PsychPref.EXTRAVERSION, 1,
                PsychPref.INTROVERSION, 0,
                PsychPref.INTUITION, -1,
                PsychPref.SENSATION, 0,
                PsychPref.THINKING, 3,
                PsychPref.FEELING, 2,
                PsychPref.PERCEPTION, -1,
                PsychPref.JUDGMENT, -2
        );
    }

    //50%/50%
    static Map<PsychPref, Double> percentageByPsych_ENTP() {
        return new EnumMap<>(
                Map.of(
                        PsychPref.EXTRAVERSION, 50.0,
                        PsychPref.INTROVERSION, 50.0,
                        PsychPref.INTUITION, 50.0,
                        PsychPref.SENSATION, 50.0,
                        PsychPref.THINKING, 50.0,
                        PsychPref.FEELING, 50.0,
                        PsychPref.PERCEPTION, 50.0,
                        PsychPref.JUDGMENT, 50.0
                )
        );
    }

    // 100%/0%
    static Map<PsychPref, Double> percentageByPsych_ESFJ() {
        return new EnumMap<>(
                Map.of(
                        PsychPref.EXTRAVERSION, 100.0,
                        PsychPref.INTROVERSION, 0.0,
                        PsychPref.INTUITION, 0.0,
                        PsychPref.SENSATION, 100.0,
                        PsychPref.THINKING, 0.0,
                        PsychPref.FEELING, 100.0,
                        PsychPref.PERCEPTION, 0.0,
                        PsychPref.JUDGMENT, 100.0
                )
        );
    }

    //75%/25%
    static Map<PsychPref, Double> percentageByPsych_INFP() {
        return new EnumMap<>(
                Map.of(
                        PsychPref.EXTRAVERSION, 25.0,
                        PsychPref.INTROVERSION, 75.0,
                        PsychPref.INTUITION, 75.0,
                        PsychPref.SENSATION, 25.0,
                        PsychPref.THINKING, 25.0,
                        PsychPref.FEELING, 75.0,
                        PsychPref.PERCEPTION, 75.0,
                        PsychPref.JUDGMENT, 25.0
                )
        );
    }

    //83.3%/16.7%
    static Map<PsychPref, Double> percentageByPsych_ISTJ() {
        return new EnumMap<>(
                Map.of(
                        PsychPref.EXTRAVERSION, 16.7,
                        PsychPref.INTROVERSION, 83.3,
                        PsychPref.INTUITION, 16.7,
                        PsychPref.SENSATION, 83.3,
                        PsychPref.THINKING, 83.3,
                        PsychPref.FEELING, 16.7,
                        PsychPref.PERCEPTION, 16.7,
                        PsychPref.JUDGMENT, 83.3
                )
        );
    }

    //58.3%/41.7%
    static Map<PsychPref, Double> percentageByPsych_ESTP() {
        return new EnumMap<>(
                Map.of(
                        PsychPref.EXTRAVERSION, 58.3,
                        PsychPref.INTROVERSION, 41.7,
                        PsychPref.INTUITION, 41.7,
                        PsychPref.SENSATION, 58.3,
                        PsychPref.THINKING, 58.3,
                        PsychPref.FEELING, 41.7,
                        PsychPref.PERCEPTION, 58.3,
                        PsychPref.JUDGMENT, 41.7
                )
        );
    }
}