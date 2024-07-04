package com.example.aluracursos.literalura.model;

import jakarta.persistence.*;

@Entity
@Table (name = "libros")
public class Libro {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column (unique = true)
    private String titulo;
    private String idiomas;
    private Double numeroDeDescargas;

    @ManyToOne
    private Autor autor;
     public Libro(){}

    public Libro(DatosLibros datosLibros) {
        this.titulo = datosLibros.titulo();
        this.autor = datosLibros.autor().stream().map(Autor :: new).toList().get(0);
        this.numeroDeDescargas = datosLibros.numeroDeDescargas();
        this.idiomas = datosLibros.idiomas().get(0);
    }

    public void  addAutor(Autor autor){
        autor.getLibros().add(this);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor.getNombre() + '\'' +
                ", idiomas='" + idiomas + '\'' +
                ", numeroDeDescargas=" + numeroDeDescargas +
                '}';
    }
}
