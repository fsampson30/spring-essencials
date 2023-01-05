package com.sampson.springessencials.service;

import com.sampson.springessencials.domain.Anime;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimeService {
    public List<Anime> listAll(){
        return List.of(new Anime(1L,"DB2"), new Anime(2L,"Berserk"));
    }
}
