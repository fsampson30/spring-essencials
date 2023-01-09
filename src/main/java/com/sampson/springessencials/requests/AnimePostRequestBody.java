package com.sampson.springessencials.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class AnimePostRequestBody {
    @NotNull(message = "The anime name cannot be empty")
    @NotEmpty(message = "The anime name cannot be null")
    private String name;

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
        AnimePostRequestBody that = (AnimePostRequestBody) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
