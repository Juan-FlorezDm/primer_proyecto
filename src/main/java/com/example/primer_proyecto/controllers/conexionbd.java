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


    @GetMapping("/conexion")
    public Object Conexion() {
        try {
            String sql = "SELECT * FROM HojaVida"; 
            List<Map<String, Object>> res = jdbcTemplate.queryForList(sql);
            return res;
        } catch (Exception e) {
            return "No se estableció la conexión" + e.getMessage();
        }
    }
    
}
