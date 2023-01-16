package com.sampson.springessencials.repository;

import com.sampson.springessencials.model.Anime;
import com.sampson.springessencials.model.DatabaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DatabaseUserRepository extends JpaRepository<DatabaseUser, Long> {

    DatabaseUser findByUsername(String username);

}
