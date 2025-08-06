package co.simplon.personalities.controllers;

import co.simplon.personalities.dtos.AnswerCreate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-dev.properties")
class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvFileSource(resources = "/submitAnswersAndGetResult_OK.csv", numLinesToSkip = 1)
    //@WithMockUser --> springSecurityTest dependency needed
    public void submitAnswersAndGetResult_OK(String json) throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .request(HttpMethod.POST, "/answers")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(builder);
        result.andExpect(status().isCreated());
    }
}