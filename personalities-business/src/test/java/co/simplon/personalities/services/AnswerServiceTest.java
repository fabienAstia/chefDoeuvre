package co.simplon.personalities.services;

import co.simplon.personalities.dtos.AnswerCreate;
import co.simplon.personalities.entities.*;
import co.simplon.personalities.repositories.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnswerServiceTest {

    @InjectMocks
    AnswerService answerService;

    @Mock
    QuestionRepository questionRepository;

    MockedTest mockedTest = new MockedTest();

    @Test
    void test_createNewAnswers() {
        //GIVEN

        User user = mockedTest.mockedUser();

        List<AnswerCreate> usersInputs = getAnswerCreates();
        Question quesiton1 = mockedQuestion1();

        Question question2 = new Question();
        question2.setLabel("label");
        question2.setOrder(2);
        question2.setPsychPreference(new PsychPreference());
        question2.setConstraint(new Constraint());

        Question quesiton3 = mockedQuetion2();
        Question quesiton4 = new Question(
                "label",
                4,
                new PsychPreference(), new Constraint()
        );
        Question quesiton5 = new Question(
                "label",
                5,
                new PsychPreference(), new Constraint()
        );


        Set<Answer> expected = Set.of(
                new Answer(1L, user, quesiton1, -3),
                new Answer(2L, user, question2, 2),
                new Answer(3L, user, quesiton3, 0),
                new Answer(4L, user, quesiton4, -1),
                new Answer(5L, user, quesiton5, 3)
        );


        //WHEN
        when(questionRepository.findById(1L)).thenReturn(Optional.of(quesiton1));
        when(questionRepository.findById(2L)).thenReturn(Optional.of(new Question(
                "label",
                2,
                new PsychPreference(), new Constraint()
        )));
        when(questionRepository.findById(3L)).thenReturn(Optional.of(mockedQuetion2()));
        when(questionRepository.findById(4L)).thenReturn(Optional.of(new Question(
                "label",
                4,
                new PsychPreference(), new Constraint()
        )));
        when(questionRepository.findById(5L)).thenReturn(Optional.of(new Question(
                "label",
                5,
                new PsychPreference(), new Constraint()
        )));


        //THEN
        var result = answerService.createNewAnswers(usersInputs, user);
        assertEquals(expected, result);
        //  assertEquals(, result.);

    }

    private static Question mockedQuetion2() {
        return new Question(
                "label",
                3,
                new PsychPreference(), new Constraint()
        );
    }

    private static Question mockedQuestion1() {
        return new Question(
                "label",
                1,
                new PsychPreference(), new Constraint()
        );
    }

    private static List<AnswerCreate> getAnswerCreates() {
        List<AnswerCreate> usersInputs = List.of(
                new AnswerCreate(
                        1L, -3),
                new AnswerCreate(
                        2L, 2
                ),
                new AnswerCreate(
                        3L, 0
                ),
                new AnswerCreate(
                        4L, -1
                ),
                new AnswerCreate(5L, 3)
        );
        return usersInputs;
    }

    @Test
    void test_getUserAndSubmitAnswers() {
        List<AnswerCreate> réponses = getAnswerCreates();
    }
}