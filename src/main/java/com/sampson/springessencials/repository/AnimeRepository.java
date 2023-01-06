package com.sampson.springessencials.repository;

import com.sampson.springessencials.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeRepository extends JpaRepository<Anime, Long> {

}
