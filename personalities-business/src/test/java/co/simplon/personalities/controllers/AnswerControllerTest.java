package co.simplon.personalities.controllers;

import co.simplon.personalities.dtos.AnswerCreate;
import co.simplon.personalities.dtos.ResultView;
import co.simplon.personalities.services.AnswerService;
import co.simplon.personalities.services.PsychPref;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
//@TestPropertySource("classpath:application-dev.properties")
@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class)
class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private AnswerControllerTest answerControllerTest;

    @Mock
    private AnswerService service;

    @ParameterizedTest
    @CsvFileSource(resources = "/submitAnswersAndGetResult_OK.csv", numLinesToSkip = 1, delimiter = '$')
    public void submitAnswersAndGetResult_OK(String jsonInput) throws Exception {
        when(service.submitAnswersAndGetResult(List.of())).thenReturn(mockedResult());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .request(HttpMethod.POST, "/answers")
                .content(jsonInput)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(builder)
                .andExpect(status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        ResultView resultView = objectMapper.readValue(json, ResultView.class);

        assertNotNull(resultView);
//        assertEquals(1L, resultView..getId());
//        assertEquals("Learn Spring Boot", article.getTitle());
    }

    ResultView mockedResult() {
        return new ResultView(
                "INFJ",
                "Le visionnaire",
                "Personne qui...",
                1.5,
                "2% slmt de la population",
                "image",
                List.of("profession"),
                Map.of("Strength", "stregth1", "Weakness", "weak1"),
                Map.of(PsychPref.EXTRAVERSION, 3.4)
        );
    }
}
//        List<AnswerCreate> expected = List.of(new AnswerCreate(1L, 2),
//                new AnswerCreate(2L, 0),
//                new AnswerCreate(3L, -1),
//                new AnswerCreate(4L, 3),
//                new AnswerCreate(5L, -3));


