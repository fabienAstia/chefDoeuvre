package co.simplon.personalities.constraints;

import co.simplon.personalities.controllers.AnswerController;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(value = "test")
class AnswerControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnswerController answerController;

    @ParameterizedTest
    @CsvFileSource(resources = "/csv/constraints/submit-answers-and-get-result_valid.csv", numLinesToSkip = 1, delimiter = '$')
    @WithMockUser
    public void shouldBeAuthorized(String jsonInput) throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .request(HttpMethod.POST, "/answers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInput);

        mockMvc.perform(builder)
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csv/constraints/submit-answers-and-get-result_not-valid.csv", numLinesToSkip = 1, delimiter = '$')
    @WithMockUser
    public void shouldBeDenied(String jsonInput) throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .request(HttpMethod.POST, "/answers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInput);

        mockMvc.perform(builder)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof MethodArgumentNotValidException));
    }
}