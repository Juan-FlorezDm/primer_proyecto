package com.example.primer_proyecto.controllers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final JdbcTemplate jdbcTemplate;

    public HomeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/")
    public String home(Model model) {
        try {
            // Contar total de registros
            String countSql = "SELECT COUNT(*) FROM HojaVida";
            Integer totalRegistros = jdbcTemplate.queryForObject(countSql, Integer.class);
            model.addAttribute("totalRegistros", totalRegistros != null ? totalRegistros : 0);
            
            // Obtener el último registro por el número (asumiendo que se insertan en orden)
            // Si no funciona, podemos usar el primer registro de la tabla
            String lastSql = "SELECT nombre FROM HojaVida ORDER BY numero DESC LIMIT 1";
            try {
                String ultimoRegistro = jdbcTemplate.queryForObject(lastSql, String.class);
                model.addAttribute("ultimoRegistro", ultimoRegistro);
            } catch (Exception e) {
                // Si hay error, mostramos el primer registro
                String firstSql = "SELECT nombre FROM HojaVida LIMIT 1";
                try {
                    String primerRegistro = jdbcTemplate.queryForObject(firstSql, String.class);
                    model.addAttribute("ultimoRegistro", primerRegistro);
                } catch (Exception ex) {
                    model.addAttribute("ultimoRegistro", "No hay registros");
                }
            }
            
        } catch (Exception e) {
            // En caso de error en la conexión
            model.addAttribute("totalRegistros", 0);
            model.addAttribute("ultimoRegistro", "Error en BD");
        }

        return "index";
    }
}