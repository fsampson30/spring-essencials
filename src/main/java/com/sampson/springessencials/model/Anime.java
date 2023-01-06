package com.sampson.springessencials.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tb_anime")
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Anime anime = (Anime) o;
        return Objects.equals(id, anime.id) && Objects.equals(name, anime.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
