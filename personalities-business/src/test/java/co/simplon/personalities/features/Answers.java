package co.simplon.personalities.features;

import co.simplon.personalities.config.SecurityHelper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(value = "test")
@Transactional
public class Answers {

    @MockBean
    SecurityHelper securityHelper;

    void shouldSubmitAnswersAndGetResult() {
        when(securityHelper.principal()).thenReturn("test@gmail.com");
    }
}
