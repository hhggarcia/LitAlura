package com.alura.LiterAlura.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdAutor;
    @Column(unique = true)
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaMuerte;

    @ManyToMany(mappedBy = "autores", fetch = FetchType.EAGER)
    private List<Book> libros;

    public Autor(){}

    public Autor(DatosAutor a){
        this.nombre = a.nombre();
        this.fechaNacimiento = a.fechaNacimiento();
        this.fechaMuerte = a.fechaMuerte();
    }

    @Override
    public String toString() {
        return "********** AUTOR **********" + "\n" +
                "| nombre: " + nombre + "\n" +
                "| fechaNacimiento: " + fechaNacimiento +"\n" +
                "| fechaMuerte: " + fechaMuerte +"\n" +
                "***************************";
    }

    public Long getId() {
        return IdAutor;
    }

    public void setId(Long id) {
        IdAutor = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(int fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getFechaMuerte() {
        return fechaMuerte;
    }

    public void setFechaMuerte(int fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }

    public List<Book> getLibros() {
        return libros;
    }

    public void setLibros(List<Book> libros) {
        this.libros = libros;
    }
}
