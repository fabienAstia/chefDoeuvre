package co.simplon.personalities.controllers;

import co.simplon.personalities.dtos.AnswerCreate;
import co.simplon.personalities.dtos.ResultView;
import co.simplon.personalities.services.AnswerService;
import co.simplon.personalities.services.PsychPref;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
@ActiveProfiles("dev")
//@ExtendWith(MockitoExtension.class)
class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //    @InjectMocks
    @MockBean
    private AnswerController answerController;

//    @Mock
//    private AnswerService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/submitAnswersAndGetResult_OK.csv", numLinesToSkip = 1, delimiter = '$')
    @WithMockUser
    public void submitAnswersAndGetResult_OK(String jsonInput) throws Exception {
        //when(service.submitAnswersAndGetResult(List.of())).thenReturn(mockedResult());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .request(HttpMethod.POST, "/answers")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInput);

        MvcResult result = mockMvc.perform(builder)
                .andExpect(status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        ResultView resultView = objectMapper.readValue(json, ResultView.class);

        assertNotNull(resultView);
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


