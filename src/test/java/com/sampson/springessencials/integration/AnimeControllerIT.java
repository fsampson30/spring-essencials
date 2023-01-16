package com.sampson.springessencials.integration;

import com.sampson.springessencials.model.Anime;
import com.sampson.springessencials.model.DatabaseUser;
import com.sampson.springessencials.repository.AnimeRepository;
import com.sampson.springessencials.repository.DatabaseUserRepository;
import com.sampson.springessencials.requests.AnimePostRequestBody;
import com.sampson.springessencials.util.AnimeCreator;
import com.sampson.springessencials.util.AnimePostRequestBodyCreator;
import com.sampson.springessencials.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayName("Integration Test")
class AnimeControllerIT {
    @Autowired
    @Qualifier(value = "testRestTemplateRoleUser")
    private TestRestTemplate testRestTemplateRoleUser;

    @Autowired
    @Qualifier(value = "testRestTemplateRoleAdmin")
    private TestRestTemplate testRestTemplateRoleAdmin;
    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private DatabaseUserRepository databaseUserRepository;

    private static final DatabaseUser USER = DatabaseUser.builder()
            .name("Flavio Sampson")
            .password("{bcrypt}$2a$10$LNp.69WcIfQgb.ugdwCTxed0Glb8JV6LxSDq281A4y0OnQdQYeuKu")
            .username("sampson")
            .authorities("ROLE_USER")
            .build();

    private static final DatabaseUser ADMIN = DatabaseUser.builder()
            .name("Flavio Sampson")
            .password("{bcrypt}$2a$10$LNp.69WcIfQgb.ugdwCTxed0Glb8JV6LxSDq281A4y0OnQdQYeuKu")
            .username("flavio")
            .authorities("ROLE_USER,ROLE_ADMIN")
            .build();

    @TestConfiguration
    @Lazy
    static class Config {
        @Bean(name = "testRestTemplateRoleUser")
        public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:"+port)
                    .basicAuthentication("sampson", "academy");
            return new TestRestTemplate(restTemplateBuilder);
        }
        @Bean(name = "testRestTemplateRoleAdmin")
        public TestRestTemplate testRestTemplateRoleAdminCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:"+port)
                    .basicAuthentication("flavio", "academy");
            return new TestRestTemplate(restTemplateBuilder);
        }

    }

    @Test
    @DisplayName("List returns list of anime inside page object when successful")
    void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful() {
        databaseUserRepository.save(USER);
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();

        PageableResponse<Anime> animePage = testRestTemplateRoleUser.exchange("/animes", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Anime>>() {
                }).getBody();

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("ListAll returns list of anime when successful")
    void list_ReturnsListOfAnimes_WhenSuccessful() {
        databaseUserRepository.save(USER);
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();

        List<Anime> animePage = testRestTemplateRoleUser.exchange("/animes/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animePage).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animePage.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns anime when successful")
    void findById_ReturnsAnime_WhenSuccessful() {
        databaseUserRepository.save(USER);
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        Long expectedId = savedAnime.getId();
        Anime anime = testRestTemplateRoleUser.getForObject("/animes/{id}", Anime.class, expectedId);
        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns a list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        databaseUserRepository.save(USER);
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();
        String url = String.format("/animes/find?name=%s", expectedName);

        List<Anime> animes = testRestTemplateRoleUser.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
        databaseUserRepository.save(USER);
        String expectedName = "aaa";
        String url = String.format("/animes/find?name=%s", expectedName);

        List<Anime> animes = testRestTemplateRoleUser.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes).isNotNull().isEmpty();

    }

    @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {
        databaseUserRepository.save(USER);
        AnimePostRequestBody animePostRequestBodyToBeSaved = AnimePostRequestBodyCreator.createAnimePostRequestBodyToBeSaved();
        ResponseEntity<Anime> entity = testRestTemplateRoleUser.postForEntity("/animes", animePostRequestBodyToBeSaved, Anime.class);
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(entity.getBody()).isNotNull();
        Assertions.assertThat(entity.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("replace update anime when successful")
    void replace_UpdateAnime_WhenSuccessful() {
        databaseUserRepository.save(USER);
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        savedAnime.setName("new name");

        ResponseEntity<Void> entity = testRestTemplateRoleUser.exchange("/animes", HttpMethod.PUT,
                new HttpEntity<>(savedAnime), Void.class);

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful() {
        databaseUserRepository.save(ADMIN);
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        ResponseEntity<Void> entity = testRestTemplateRoleAdmin.exchange("/animes/admin/{id}", HttpMethod.DELETE, null, Void.class, savedAnime.getId());

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete return 403 when user is not admin")
    void delete_Return403_WhenUserIsNotAdmin() {
        databaseUserRepository.save(USER);
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        ResponseEntity<Void> entity = testRestTemplateRoleUser.exchange("/animes/admin/{id}", HttpMethod.DELETE, null, Void.class, savedAnime.getId());

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

}
