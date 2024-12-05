package com.alura.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(@JsonAlias("birth_year") Integer fechaNacimiento,
                         @JsonAlias("death_year") Integer fechaMuerte,
                         @JsonAlias("name") String nombre) {
}
