package com.example.primer_proyecto.controllers;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.example.primer_proyecto.model.HojaVida;
import jakarta.validation.Valid;


@Controller
public class insertarhojavida {
    private final JdbcTemplate jdbcTemplate;

    public insertarhojavida(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


   @PostMapping("/insertarhojavida")
    public String saveUser(
            @Valid HojaVida hojaVida,
            BindingResult result,
            @RequestParam("pdf") MultipartFile pdf,
            Model model) {

        if (result.hasErrors()) {
            return "registrarhojavida"; // 👈 vuelve al form y muestra errores
        }

        try {
            String sql = "INSERT INTO HojaVida (nombre, numero, descripcion, estudios, pdf, email) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(
                    sql,
                    hojaVida.getNombre(),
                    hojaVida.getNumero(),
                    hojaVida.getDescripcion(),
                    hojaVida.getEstudios(),
                    pdf.getBytes(),
                    hojaVida.getEmail()
            );
            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/error";
        }
    }
}