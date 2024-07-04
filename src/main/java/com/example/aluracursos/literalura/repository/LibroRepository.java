package com.example.aluracursos.literalura.repository;

import com.example.aluracursos.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository <Libro, Long>{
    List<Libro> findByIdiomasIgnoreCase(String idiomas);

}
