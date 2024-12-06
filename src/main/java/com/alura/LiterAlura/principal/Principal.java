package com.alura.LiterAlura.principal;

import com.alura.LiterAlura.model.*;
import com.alura.LiterAlura.repository.AutorRepository;
import com.alura.LiterAlura.repository.BookRepository;
import com.alura.LiterAlura.services.ConvertData;
import com.alura.LiterAlura.services.GuntendexAPI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private GuntendexAPI guntendex = new GuntendexAPI();
    private final String API_BASE = "https://gutendex.com";
    private ConvertData convertData = new ConvertData();
    private BookRepository repositorio;
    private AutorRepository repoAutor;
    private Optional<Book> libro;

    public Principal(BookRepository bookRepository,
                     AutorRepository autorRepository){
        this.repositorio = bookRepository;
        this.repoAutor = autorRepository;

    }

    public void menuPrincipal(){
        var opcion = -1;

        while(opcion != 0){
            var menu = """
                     1 - Buscar libro por titulo.
                     2 - Mostrar libros registrados.
                     3 - Mostrar autores registrados.
                     4 - Autores vivos en un año.
                     5 - Mostrar libros por idiomas.
                     0 - Salir
                     """;

            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    buscarAutoresPorAnio();
                    break;
                case 5:
                    mostrarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Ingrese el nombre del libro que desea buscar: ");
        var titulo = teclado.nextLine();
        var json = guntendex.obtenerDatos(API_BASE + "/books?search=" + titulo.replace(" ", "%20"));

        if (json == null || json.trim().isEmpty()) {
            System.out.println("Ocurrió un error en la consulta. Inténtelo nuevamente");
        } else {
            DatosRequest datos = convertData.getDatos(json, DatosRequest.class);
            if (datos.cantidad() == 0) {
                System.out.println("No se encontraron libros para: " + titulo);
            } else {
                //datos.resultado().forEach(System.out::println);
                Optional<DatosBook> datosLibroBuscado = datos.resultado().stream().findFirst();

                if (datosLibroBuscado.isPresent()) {
                    Book libroBuscado = new Book(datosLibroBuscado.get());

                    // Verificar si el libro ya existe en la base de datos
                    Optional<Book> libro = repositorio.findByTituloContainsIgnoreCase(libroBuscado.getTitulo());

                    if (libro.isPresent()) {
                        System.out.println("El libro ya existe en la base de datos: \n" + libro.get());
                    } else {
                        // Registrar autores antes del libro
                        for (Autor autor : libroBuscado.getAutor()) {
                            Optional<Autor> autorExistente = repoAutor.findByNombre(autor.getNombre());

                            if (autorExistente.isPresent()) {
                                System.out.println("Ya existe el autor: \n" + autor.getNombre());
                            } else {
                                repoAutor.save(autor);
                            }
                        }
                        // Registrar libro
                        repositorio.save(libroBuscado);
                        System.out.println("Libro registrado: \n" + libroBuscado);
                    }
                } else {
                    System.out.println("No se encontraron libros para: " + titulo);
                }
            }
        }
    }

    private void mostrarLibrosRegistrados() {
        //System.out.println("Hola mundo 2");
        List<Book> libros = repositorio.findAll();
        libros.forEach(System.out::println);
    }

    private void mostrarAutoresRegistrados() {
        //System.out.println("Hola mundo 3");
        List<Autor> autores = repoAutor.findAll();
        autores.forEach(System.out::println);
    }

    private void buscarAutoresPorAnio() {
        //System.out.println("Hola mundo 4");
        System.out.println("Ingrese el año para buscar autores vivos: ");
        Integer year = teclado.nextInt();
        List<Autor> authors = repoAutor.findAuthorsAliveInYear(year);
        if (authors.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + year);
        } else {
            System.out.println("Autores vivos en el año " + year + ":\n");
            authors.forEach(author -> System.out.println(author.toString()));
        }
    }

    private void mostrarLibrosPorIdioma() {
        //System.out.println("Hola mundo 5");
        System.out.println("Ingrese el idioma para buscar libros (ES, EN, FR, PT): ");
        String input = teclado.nextLine().toUpperCase();

        try {
            Idioma idioma = Idioma.valueOf(input);
            System.out.println("Libros en el idioma " + idioma + ":\n");
            List<Book> books = repositorio.findByLenguajes(idioma);
            if (books.isEmpty()) {
                System.out.println("No se encontraron libros en el idioma " + idioma);
            } else {
                books.forEach(book -> System.out.println(book.toString()));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Idioma no válido. Por favor, ingrese uno de los siguientes: ES, EN, FR, PT");
        }

    }
}
