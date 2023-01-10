package com.sampson.springessencials.util;

import com.sampson.springessencials.model.Anime;

public class AnimeCreator {

    public static Anime createAnimeToBeSaved() {
        return Anime.builder()
                .name("Test Anime")
                .build();
    }

    public static Anime createValidAnime() {
        return Anime.builder()
                .name("Test Anime")
                .id(1l)
                .build();
    }

    public static Anime createValidUpdatedAnime() {
        return Anime.builder()
                .name("Test Anime Updated")
                .id(1l)
                .build();
    }
}
