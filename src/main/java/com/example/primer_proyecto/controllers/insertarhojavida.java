package com.example.primer_proyecto.controllers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class insertarhojavida {
    private final JdbcTemplate jdbcTemplate;

    public insertarhojavida(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    
    @PostMapping("/insertarhojavida")
    public String saveUser(@RequestParam("id_user") int id_user,
                           @RequestParam("nombre") String nombre,
                           @RequestParam("email") String email) {
        String sql = "INSERT INTO HojasVida (id_user, nombre, email) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, id_user, nombre, email);
        return "redirect:/"; 
    }

}
