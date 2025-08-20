package com.example.primer_proyecto.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class conexionbd {

    private final JdbcTemplate jdbcTemplate;

    public conexionbd(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @GetMapping("/users")
    public List<Map<String, Object>> getuser() {
        String consulta = "select * from Users";
        return jdbcTemplate.queryForList(consulta);
    }


    @GetMapping("/conexion")
    public String Conexion() {
        try {
            jdbcTemplate.execute("select * from users");
            return "Conexión exitosaaaa";
        } catch (Exception e) {
            return "No se estableció la conexión" + e.getMessage();
        }
    }
    
}
