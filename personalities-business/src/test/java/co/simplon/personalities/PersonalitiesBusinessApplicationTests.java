package co.simplon.personalities;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("Disabled for CI/CD build to avoid DB errors")
@SpringBootTest
class PersonalitiesBusinessApplicationTests {

    @Test
    void contextLoads() {
    }

}
