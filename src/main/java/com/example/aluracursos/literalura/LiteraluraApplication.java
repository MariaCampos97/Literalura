package com.example.aluracursos.literalura;

import com.example.aluracursos.literalura.model.DatosLibros;
import com.example.aluracursos.literalura.principal.Principal;
import com.example.aluracursos.literalura.repository.AutorRepository;
import com.example.aluracursos.literalura.repository.LibroRepository;
import com.example.aluracursos.literalura.service.ConsumoAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	LibroRepository libroRepository;
	@Autowired
	AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepository, autorRepository);
		principal.muestraElMenu();
	}
}
