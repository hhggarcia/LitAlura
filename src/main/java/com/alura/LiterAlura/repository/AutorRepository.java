package com.alura.LiterAlura.repository;

import com.alura.LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombre(String nombreAutor);
    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :year AND (a.fechaMuerte IS NULL OR a.fechaMuerte >= :year)")
    List<Autor> findAuthorsAliveInYear(@Param("year") Integer year);
}
