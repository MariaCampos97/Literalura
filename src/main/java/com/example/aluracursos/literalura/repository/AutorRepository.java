package com.example.aluracursos.literalura.repository;

import com.example.aluracursos.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository <Autor, Long>{

    Autor findByNombre (String nombre);

    @Query ("SELECT a FROM Autor a WHERE a.fechaDeNacimiento <=:ano AND a.fechaDeFallecimiento >=:ano")
    List <Autor>buscarAutoresVivosPorAno(int ano);

}
