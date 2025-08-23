package co.simplon.personalities.features;

import co.simplon.personalities.config.SecurityHelper;
import co.simplon.personalities.dtos.AnswerCreate;
import co.simplon.personalities.dtos.AnswerCreateList;
import co.simplon.personalities.dtos.ResultView;
import co.simplon.personalities.exceptions.UserNotFoundException;
import co.simplon.personalities.repositories.AnswerRepository;
import co.simplon.personalities.repositories.MbtiTypeRepository;
import co.simplon.personalities.repositories.QuestionRepository;
import co.simplon.personalities.repositories.UserRepository;
import co.simplon.personalities.services.AnswerService;
import co.simplon.personalities.services.PsychPref;
import co.simplon.personalities.services.ResultService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles(value = "test")
@Transactional
class AnswersIntegrationTest {

    @MockBean
    SecurityHelper securityHelper;

    @Autowired
    AnswerService answerService;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MbtiTypeRepository mbtiTypeRepository;

    @Autowired
    ResultService resultService;

    @Test
    void shouldSubmitAnswersAndGetResult_OK() {
        when(securityHelper.principal()).thenReturn("test@gmail.com");
        var actual = answerService.submitAnswersAndGetResult(mockedAnswers());
        var expected = mockedResultView();

        assertThat(actual).isEqualTo(expected)
                .extracting(ResultView::getCode, ResultView::getName,
                        ResultView::getPopulationPercentage, ResultView::getProfessions)
                .containsExactly("INTJ", "L'Architecte", 2.1, List.of("Ingénieur IT"));
    }

    @Test
    void shouldSubmitAnswersAndGetResult_throws_UserNotFoundException() {
        when(securityHelper.principal()).thenReturn("unknown@gmail.com");

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            answerService.submitAnswersAndGetResult(mockedAnswers());
        });
        String expectedMessage = String.format("User with username: unknown@gmail.com is not found");
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    private static ResultView mockedResultView() {
        return new ResultView(
                "INTJ",
                "L'Architecte",
                "Les INTJ sont des penseurs stratégiques et indépendants, axés sur l’efficacité et la réalisation d’objectifs ambitieux.",
                2.1,
                "Beaucoup de chefs d’entreprise et de stratèges sont INTJ, notamment Elon Musk.",
                "INTJ.webp",
                List.of("Ingénieur IT"),
                strengthsAndWeaknesses(),
                statsPerPsych()
        );
    }

    private static Map<PsychPref, Double> statsPerPsych() {
        LinkedHashMap linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(PsychPref.EXTRAVERSION, 0.0);
        linkedHashMap.put(PsychPref.INTROVERSION, 100.0);
        linkedHashMap.put(PsychPref.INTUITION, 100.0);
        linkedHashMap.put(PsychPref.SENSATION, 0.0);
        linkedHashMap.put(PsychPref.THINKING, 100.0);
        linkedHashMap.put(PsychPref.FEELING, 0.0);
        linkedHashMap.put(PsychPref.PERCEPTION, 0.0);
        linkedHashMap.put(PsychPref.JUDGMENT, 100.0);
        return linkedHashMap;
    }

    private static Map<String, String> strengthsAndWeaknesses() {
        LinkedHashMap linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("Stratégique", "Force");
        linkedHashMap.put("Froid", "Faiblesse");
        return linkedHashMap;
    }

    private static AnswerCreateList mockedAnswers() {
        return new AnswerCreateList(List.of(
                new AnswerCreate(1L, -3),
                new AnswerCreate(2L, 3),
                new AnswerCreate(3L, 3),
                new AnswerCreate(4L, -3),
                new AnswerCreate(5L, 3),
                new AnswerCreate(6L, -3),
                new AnswerCreate(7L, -3),
                new AnswerCreate(8L, 3)
        ));
    }
}
