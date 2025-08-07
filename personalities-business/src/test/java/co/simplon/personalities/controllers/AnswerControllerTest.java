package co.simplon.personalities.controllers;

import co.simplon.personalities.dtos.AnswerCreate;
import co.simplon.personalities.services.AnswerService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource("classpath:application-dev.properties")
class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnswerService service;

    @ParameterizedTest
    @CsvFileSource(resources = "/submitAnswersAndGetResult_OK.csv", numLinesToSkip = 1)
    //@WithMockUser --> springSecurityTest dependency needed
    public void submitAnswersAndGetResult_OK(String json) throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .request(HttpMethod.POST, "/answers")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(builder);
        when(service.submitAnswersAndGetResult(List.of())).thenReturn()

        result.andExpect(status().isCreated());
    }
}