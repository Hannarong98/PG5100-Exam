package no.kristiania.exam.pg5100.backend.service;

import no.kristiania.exam.pg5100.backend.StubApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StubApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ServiceTestBase {

    @Autowired
    private ResetService resetService;

    @BeforeEach
    private void cleanDatabase() {
        resetService.resetDatabase();
    }

}
