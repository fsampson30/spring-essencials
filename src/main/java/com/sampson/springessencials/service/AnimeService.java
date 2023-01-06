package com.sampson.springessencials.service;

import com.sampson.springessencials.domain.Anime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AnimeService {

    private List<Anime> animes = List.of(new Anime(1L,"DB2"), new Anime(2L,"Berserk"));

    public List<Anime> listAll(){
        return List.of(new Anime(1L,"DB2"), new Anime(2L,"Berserk"));
    }

    public Anime findById(long id){
        return animes.stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
    }
}
