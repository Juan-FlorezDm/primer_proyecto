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
    public String registrarUsuarioHandler(@RequestParam ("correo") String correo,
                                          @RequestParam ("nombre") String nombre,
                                          @RequestParam ("contraseña") String contraseña){
        
        String registarusuario = "INSERT INTO users (nombre, email, contraseña) VALUES (?, ?, ?)";
        jdbcTemplate.update(registarusuario, nombre, correo, contraseña);
        return "redirect:/"; 
    }
    
}
