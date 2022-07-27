package com.github.dericksm.moviesinfoservice.integration;

import com.github.dericksm.moviesinfoservice.domain.dto.MovieInfoRequestDTO;
import com.github.dericksm.moviesinfoservice.domain.dto.MovieInfoResponseDTO;
import com.github.dericksm.moviesinfoservice.domain.dto.MovieInfoUpdateRequestDTO;
import com.github.dericksm.moviesinfoservice.domain.entity.MovieInfo;
import com.github.dericksm.moviesinfoservice.repository.MovieInfoRepository;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.UUID;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class MovieInfoIT {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    MovieInfoRepository movieInfoRepository;

    private static final String MOVIES_INFO_API = "/movie-info";

    private static final MovieInfo DEFAULT_MOVIE_INFO = MovieInfo.builder().id(UUID.randomUUID().toString()).name("Fight Club")
        .releaseDate(OffsetDateTime.parse("1999-10-29T09:15:30Z")).cast(Arrays.asList("Brad Pitt", "Jared Leto")).build();

    @BeforeEach
    public void setup() {
        movieInfoRepository.save(DEFAULT_MOVIE_INFO).block();
    }

    @AfterEach
    public void tearDown() {
        movieInfoRepository.deleteAll().block();
    }

    @Test
    void saveTest() {
        MovieInfoRequestDTO movieInfoRequest = MovieInfoRequestDTO
            .builder()
            .name("Fight Club")
            .releaseDate(OffsetDateTime.parse("1999-10-29T09:15:30Z"))
            .cast(Arrays.asList("Brad Pitt", "Jared Leto"))
            .build();

        webTestClient
            .post()
            .uri(MOVIES_INFO_API)
            .bodyValue(movieInfoRequest)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(MovieInfoResponseDTO.class)
            .consumeWith(movieInfoResponseResult -> {
                var movieInfoResponse = movieInfoResponseResult.getResponseBody();
                Assertions.assertNotNull(movieInfoResponse);
                Assertions.assertNotNull(movieInfoResponse.getId());
                Assertions.assertEquals(movieInfoRequest.getName(), movieInfoResponse.getName());
                Assertions.assertEquals(movieInfoRequest.getYear(), movieInfoResponse.getYear());
                Assertions.assertTrue(CollectionUtils.isEqualCollection(movieInfoRequest.getCast(), movieInfoResponse.getCast()));
            });
    }

    @Test
    void getAllTest() {
        webTestClient
            .get()
            .uri(MOVIES_INFO_API)
            .exchange()
            .expectStatus()
            .is2xxSuccessful()
            .expectBodyList(MovieInfoResponseDTO.class)
            .hasSize(1)
            .consumeWith(listEntityExchangeResult -> {
                var movieInfo = listEntityExchangeResult.getResponseBody().get(0);
                Assertions.assertEquals(DEFAULT_MOVIE_INFO.getName(), movieInfo.getName());
                Assertions.assertEquals(DEFAULT_MOVIE_INFO.getYear(), movieInfo.getYear());
                Assertions.assertEquals(DEFAULT_MOVIE_INFO.getReleaseDate(), movieInfo.getReleaseDate());
            });
    }

    @Test
    void getById() {
        webTestClient
            .get()
            .uri(MOVIES_INFO_API + "/{id}", DEFAULT_MOVIE_INFO.getId())
            .exchange()
            .expectStatus()
            .is2xxSuccessful()
            .expectBody(MovieInfoResponseDTO.class)
            .consumeWith(movieInfoResponse -> {
                var movieInfo = movieInfoResponse.getResponseBody();
                Assertions.assertEquals(DEFAULT_MOVIE_INFO.getId(), movieInfo.getId());
                Assertions.assertEquals(DEFAULT_MOVIE_INFO.getName(), movieInfo.getName());
                Assertions.assertEquals(DEFAULT_MOVIE_INFO.getYear(), movieInfo.getYear());
                Assertions.assertEquals(DEFAULT_MOVIE_INFO.getReleaseDate(), movieInfo.getReleaseDate());
            });
    }

    @Test
    void getById_shouldReturnBadRequest() {
        webTestClient
            .get()
            .uri(MOVIES_INFO_API + "/{id}", UUID.randomUUID().toString())
            .exchange()
            .expectStatus()
            .is4xxClientError();
    }

    @Test
    void update() {
        var movieInfoUpdateRequest = MovieInfoUpdateRequestDTO.builder().name("New name").year(1999).build();

        webTestClient
            .put()
            .uri(MOVIES_INFO_API + "/{id}", DEFAULT_MOVIE_INFO.getId())
            .bodyValue(movieInfoUpdateRequest)
            .exchange()
            .expectStatus()
            .is2xxSuccessful()
            .expectBody(MovieInfoResponseDTO.class)
            .consumeWith(movieInfoResponse -> {
                var movieInfo = movieInfoResponse.getResponseBody();
                Assertions.assertEquals(DEFAULT_MOVIE_INFO.getId(), movieInfo.getId());
                Assertions.assertEquals(movieInfoUpdateRequest.getName(), movieInfo.getName());
                Assertions.assertEquals(movieInfoUpdateRequest.getYear(), movieInfo.getYear());
                Assertions.assertEquals(movieInfoUpdateRequest.getReleaseDate(), movieInfo.getReleaseDate());
            });
    }

    @Test
    void delete(){

        webTestClient
            .delete()
            .uri(MOVIES_INFO_API + "/{id}"," ")
            .exchange()
            .expectStatus()
            .isNoContent();

        webTestClient
            .get()
            .uri(MOVIES_INFO_API + "/{id}", UUID.randomUUID().toString())
            .exchange()
            .expectStatus()
            .is4xxClientError();
    }

}
