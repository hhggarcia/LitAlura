package com.alura.LiterAlura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name = "Libros")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String titulo;
    @Enumerated(EnumType.STRING)
    private Idioma lenguajes;
    private Integer cantidadDescargas;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "Id"),
            inverseJoinColumns = @JoinColumn(name = "IdAutor")
    )
    private List<Autor> autores;

    public Book(){}

    public Book(DatosBook b){
        this.titulo = b.titulo();
        Optional<String> auxIdioma = b.idiomas().stream().findFirst();
        if (auxIdioma.isPresent()){
            this.lenguajes = Idioma.fromString(auxIdioma.get());
        } else{
            this.lenguajes = Idioma.EN;
        }
        this.cantidadDescargas = b.descargas();
        this.autores = b.autores().stream()
                .map(a -> new Autor(a))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "********** LIBRO **********" + "\n" +
                "| titulo:" + titulo  + "\n" +
                "| lenguajes:" + lenguajes +"\n" +
                "| cantidadDescargas:" + cantidadDescargas +"\n" +
                "***************************";
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Idioma getLenguajes() {
        return lenguajes;
    }

    public void setLenguajes(Idioma lenguajes) {
        this.lenguajes = lenguajes;
    }

    public int getCantidadDescargas() {
        return cantidadDescargas;
    }

    public void setCantidadDescargas(int cantidadDescargas) {
        this.cantidadDescargas = cantidadDescargas;
    }

    public List<Autor> getAutor() {
        return autores;
    }

    public void setAutor(List<Autor> autor) {
        this.autores = autor;
    }
}
