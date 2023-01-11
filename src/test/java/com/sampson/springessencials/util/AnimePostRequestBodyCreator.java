package com.sampson.springessencials.util;

import com.sampson.springessencials.model.Anime;
import com.sampson.springessencials.requests.AnimePostRequestBody;

public class AnimePostRequestBodyCreator {

    public static AnimePostRequestBody createAnimePostRequestBodyToBeSaved() {
        return AnimePostRequestBody.builder()
                .name(AnimeCreator.createAnimeToBeSaved().getName())
                .build();
    }
}
