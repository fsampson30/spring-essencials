package com.sampson.springessencials.controller;

import com.sampson.springessencials.domain.Anime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/anime")
public class AnimeController {

    @GetMapping("/list")
    public List<Anime> list(){
        return List.of(new Anime("DB2"), new Anime("Berserk"));
    }
}
