package com.example.aluracursos.literalura.principal;

import com.example.aluracursos.literalura.model.*;
import com.example.aluracursos.literalura.repository.AutorRepository;
import com.example.aluracursos.literalura.repository.LibroRepository;
import com.example.aluracursos.literalura.service.ConsumoAPI;
import com.example.aluracursos.literalura.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Consulta por titulo del Libro
                    2 - Mostrar libros por idioma
                    3 - Mostrar libros buscados
                    4 - Mostrar autores de libros buscados
                    5 - Mostrar autores vivos en un determinado año
                         
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    consultaTituloLibro();
                    break;
                case 2:
                    mostrarLibrosPorIdioma();
                    break;
                case 3:
                    mostrarLibrosBuscados();
                    break;
                case 4:
                    mostrarLibrosAutores();
                    break;
                case 5:
                    mostrarAutoresPorAno();
                    break;


                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }

        }
}


    private  DatosLibros getDatosLibros(){
    System.out.println("Por favor ingrese el titulo del libro que deseas buscar");
    //Busca los datos generales de los libros
    var tituloLibro = teclado.nextLine();
    var json = consumoAPI.obtenerDatos(URL_BASE + "?search=%20" + tituloLibro.replace(" ", "+"));
    System.out.println(json);
    // convierte datos de API a Json
    var datos = convierteDatos.obtenerDatos(json, DatosResultados.class);
    System.out.println(datos);
    return datos.resultados().get(0);
}

        private void consultaTituloLibro () {
            try {

                DatosLibros datos = getDatosLibros();
                Libro libro = new Libro(datos);
                Autor autor = autorRepository.findByNombre(datos.autor().get(0).nombre());
                if (autor != null) {
                    libro.addAutor(autor);
                    libro.setAutor(autor);
                } else {
                    autorRepository.save(libro.getAutor());
                }
                libroRepository.save(libro);
                System.out.println(libro);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("libro no encontrado");
            } catch (Exception e) {
                System.out.println("No se puede registrar un libro mas de una vez");

            }
        }

    private void mostrarLibrosPorIdioma() {
        String opciones = """
                Ingrese el idioma para buscar los libros:
                
                es - Español
                en - Ingles
                
                """;
        System.out.println(opciones);
        var opcion = teclado.nextLine();
        if (opcion.equalsIgnoreCase("es" )|| opcion.equalsIgnoreCase("en"))
        {
            List <Libro> libroIdioma = libroRepository.findByIdiomasIgnoreCase(opcion);
            if (libroIdioma.isEmpty()){
                System.out.println("No se encontraron libros en el idioma seleccionado");
            } else {
                libroIdioma.forEach(System.out::println);
            }
        } else {
            System.out.println("Opcion no valida");
        }

    }


    private void mostrarLibrosBuscados() {
        List<Libro> libros = libroRepository.findAll();
        libros.forEach(System.out::println);
    }


    private void mostrarLibrosAutores() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }


    private void mostrarAutoresPorAno() {
        System.out.println("Ingrese el año por el que de desea buscar: ");
        var ano = teclado.nextInt();

        List<Autor> autoresVivos = autorRepository.buscarAutoresVivosPorAno(ano);
        if (autoresVivos.isEmpty()){
            System.out.println("Ningun autor vivo encontrado en ese año");
        } else {
            autoresVivos.forEach(System.out::println);
        }
    }



}
