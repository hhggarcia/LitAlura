package com.alura.LiterAlura.repository;

import com.alura.LiterAlura.model.Book;
import com.alura.LiterAlura.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTituloContainsIgnoreCase(String title);
    List<Book> findByLenguajes(Idioma idioma);
}
