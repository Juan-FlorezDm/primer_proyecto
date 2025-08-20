package com.example.primer_proyecto.controllers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class insertarhojavida {
    private final JdbcTemplate jdbcTemplate;

    public insertarhojavida(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    
    @PostMapping("/insertarhojavida")
    public String saveUser(@RequestParam("cedula") int cedula,
                           @RequestParam("nombre") String nombre,
                           @RequestParam("email") String email,
                           @RequestParam("telefono") String telefono,
                           @RequestParam("experiencia") int experiencia,
                           @RequestParam("estudios") String estudios,
                           @RequestParam("titulo") String titulo,
                           @RequestParam("edad") int edad,
                           @RequestParam("ciudad") String ciudad) {
        String sql = "INSERT INTO HV (cedula, nombre, email, telefono, experiencia, titulo, estudios, edad, Ciudad) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, cedula, nombre, email, telefono, experiencia, titulo, estudios, edad, ciudad);
        return "redirect:/"; 
    }

}
