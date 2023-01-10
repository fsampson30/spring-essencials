package com.sampson.springessencials.client;

import com.sampson.springessencials.model.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class,2);
        log.info("Entity: " + entity);

        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 2);
        log.info("Object: " + object);

        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
        log.info("Array: " + Arrays.toString(animes));

        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() { });
        log.info("List: " + exchange);

        Anime newAnime = Anime.builder().name("Auto Save").build();
        Anime savedAnime = new RestTemplate().postForObject("http://localhost:8080/animes",newAnime, Anime.class);
        log.info("saved anime {}",savedAnime);
    }
}
