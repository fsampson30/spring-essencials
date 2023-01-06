package com.sampson.springessencials.service;

import com.sampson.springessencials.model.Anime;
import com.sampson.springessencials.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AnimeService {

    @Autowired
    private AnimeRepository animeRepository;

    public List<Anime> listAll() {
        return animeRepository.findAll();
    }

    public Anime findById(long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
    }

    public Anime save(Anime anime) {
        return animeRepository.save(anime);
    }

    public void deleteById(long id) {
        animeRepository.deleteById(id);
    }

    /*public void replace(Anime anime) {
        deleteById(anime.getId());
        animes.add(anime);
    }*/
}
