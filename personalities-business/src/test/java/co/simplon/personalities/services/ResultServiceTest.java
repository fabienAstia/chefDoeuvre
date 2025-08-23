package co.simplon.personalities.services;

import co.simplon.personalities.entities.MbtiType;
import co.simplon.personalities.repositories.MbtiTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResultServiceTest {

    @InjectMocks
    ResultService resultService;

    @Mock
    MbtiTypeRepository mbtiTypeRepository;

    @Test
    void test_getMbtiTypeCode() {
        Map<PsychPref, Integer> scoreByPsych = getMockedScoreByPsych();
        MbtiType mbtiType = new MbtiType();
        mbtiType.setCode("INFJ");

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        when(mbtiTypeRepository.findProjectedByCode(argument.capture()))
                .thenReturn(mbtiType);
        var result = resultService.getMbtiTypeCode(scoreByPsych);
        String mbtiTypeCode = argument.getValue();

        assertEquals("INFJ", mbtiTypeCode);
        assertEquals("INFJ", result.getCode());
    }

    @Test
    void test_getPercentageByPsych() {
        Map<PsychPref, Integer> scoreByPsych = getMockedScoreByPsychForPercentage();
        resultService.setStaticNumberQuestions(24);
        var result = resultService.getPercentageByPsych(scoreByPsych);
        Map<PsychPref, Double> expected = new LinkedHashMap<>();
        expected.put(PsychPref.EXTRAVERSION, 100.0);
        expected.put(PsychPref.INTROVERSION, 0.0);
        expected.put(PsychPref.INTUITION, 50.0);
        expected.put(PsychPref.SENSATION, 50.0);
        expected.put(PsychPref.THINKING, 33.3);
        expected.put(PsychPref.FEELING, 66.7);
        expected.put(PsychPref.PERCEPTION, 0.0);
        expected.put(PsychPref.JUDGMENT, 100.0);
        assertEquals(expected, result);
    }

    private static Map<PsychPref, Integer> getMockedScoreByPsych() {
        Map<PsychPref, Integer> scoreByPsych = new HashMap<>();
        scoreByPsych.put(PsychPref.EXTRAVERSION, -25);
        scoreByPsych.put(PsychPref.INTROVERSION, -15);
        scoreByPsych.put(PsychPref.INTUITION, 19);
        scoreByPsych.put(PsychPref.SENSATION, 2);
        scoreByPsych.put(PsychPref.THINKING, 0);
        scoreByPsych.put(PsychPref.FEELING, 23);
        scoreByPsych.put(PsychPref.PERCEPTION, -4);
        scoreByPsych.put(PsychPref.JUDGMENT, 23);
        return scoreByPsych;
    }

    private static Map<PsychPref, Integer> getMockedScoreByPsychForPercentage() {
        Map<PsychPref, Integer> scoreByPsych = new HashMap<>();
        scoreByPsych.put(PsychPref.EXTRAVERSION, 0);
        scoreByPsych.put(PsychPref.INTROVERSION, -18);
        scoreByPsych.put(PsychPref.INTUITION, 9);
        scoreByPsych.put(PsychPref.SENSATION, 9);
        scoreByPsych.put(PsychPref.THINKING, 6);
        scoreByPsych.put(PsychPref.FEELING, 12);
        scoreByPsych.put(PsychPref.PERCEPTION, -12);
        scoreByPsych.put(PsychPref.JUDGMENT, 6);
        return scoreByPsych;
    }
}