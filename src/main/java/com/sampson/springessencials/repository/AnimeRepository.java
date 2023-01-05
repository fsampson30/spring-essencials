package com.sampson.springessencials.repository;

import com.sampson.springessencials.domain.Anime;
import com.sampson.springessencials.service.AnimeService;

import java.util.List;

public interface AnimeRepository {
    List<Anime> listAll();
}
