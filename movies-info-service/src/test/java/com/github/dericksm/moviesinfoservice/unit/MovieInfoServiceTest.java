package com.github.dericksm.moviesinfoservice.unit;

import com.github.dericksm.moviesinfoservice.service.MovieInfoService;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class MovieInfoServiceTest {

    @Autowired
    MovieInfoService movieInfoService;

    @Test
    public void saveWithNullObject_shouldReturnConstraintViolationException() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            movieInfoService.save(null);
        });
    }

}
