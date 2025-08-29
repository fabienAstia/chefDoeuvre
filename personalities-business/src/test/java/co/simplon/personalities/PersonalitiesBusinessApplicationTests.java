package co.simplon.personalities;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

//@Disabled("Disabled for CI/CD build to avoid DB errors")
@ActiveProfiles("test")
@SpringBootTest
class PersonalitiesBusinessApplicationTests {

    @Test
    void contextLoads() {
    }

}
