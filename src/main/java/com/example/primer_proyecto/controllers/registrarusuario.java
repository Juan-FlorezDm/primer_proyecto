package com.example.primer_proyecto.controllers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class registrarusuario {
    
     private final JdbcTemplate jdbcTemplate;

    public registrarusuario(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @PostMapping("/user/registro")
    public String registrarUsuarioHandler(@RequestParam ("cedula") int cedula,
                                          @RequestParam ("nombre") String nombre,
                                          @RequestParam ("email") String email,
                                          @RequestParam ("contraseña") String contraseña) {   
        String registarusuario = "INSERT INTO Users (cedula, nombre, email, contraseña) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(registarusuario, cedula, nombre, email, contraseña);
        return "redirect:/consultar_hojas"; 
    }
    
}
