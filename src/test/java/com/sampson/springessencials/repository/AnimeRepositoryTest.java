package com.sampson.springessencials.repository;

import com.sampson.springessencials.model.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
class AnimeRepositoryTest {
    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save creates anime when successful")
    void save_PersistAnime_WhenSuccessful(){
        Anime animeToBeSaved = createAnime();
        Anime savedAnime = this.animeRepository.save(animeToBeSaved);
        Assertions.assertThat(savedAnime).isNotNull();
        Assertions.assertThat(savedAnime.getId()).isNotNull();
        Assertions.assertThat(savedAnime.getName()).isEqualTo(animeToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates anime when successful")
    void save_UpdateAnime_WhenSuccessful(){
        Anime animeToBeSaved = createAnime();
        Anime savedAnime = this.animeRepository.save(animeToBeSaved);
        savedAnime.setName("Updated Anime");
        Anime animeUpdated = this.animeRepository.save(savedAnime);
        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeUpdated.getId()).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(savedAnime.getName());
    }

    @Test
    @DisplayName("Deletes removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful(){
        Anime animeToBeSaved = createAnime();
        Anime savedAnime = this.animeRepository.save(animeToBeSaved);
        this.animeRepository.delete(savedAnime);
        Optional<Anime> animeOptional = this.animeRepository.findById(savedAnime.getId());
        Assertions.assertThat(animeOptional).isEmpty();
    }

    @Test
    @DisplayName("Find by name returns list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        Anime animeToBeSaved = createAnime();
        Anime savedAnime = this.animeRepository.save(animeToBeSaved);
        String name = savedAnime.getName();
        List<Anime> animes = this.animeRepository.findByName(name);
        Assertions.assertThat(animes).isNotEmpty();
        Assertions.assertThat(animes).contains(animeToBeSaved);
    }

    @Test
    @DisplayName("Find by name returns empty list when no anims is found")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound(){
        Anime animeToBeSaved = createAnime();
        Anime savedAnime = this.animeRepository.save(animeToBeSaved);
        List<Anime> animes = this.animeRepository.findByName("name");
        Assertions.assertThat(animes).isEmpty();
    }



    private Anime createAnime(){
        return Anime.builder().
                name("Test Anime")
                .build();
    }

}