package com.example.primer_proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.stereotype.Controller;


@Controller
@SpringBootApplication
public class PrimerProyectoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrimerProyectoApplication.class, args);
	}
}
