package com.example.primer_proyecto.controllers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
            RedirectAttributes redirectAttributes) { // Cambiamos Model por RedirectAttributes

        if (result.hasErrors()) {
            // Si hay errores de validación, regresamos al formulario
            return "registrarhojavida";
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
            
            // ✅ MENSAJE DE ÉXITO
            redirectAttributes.addFlashAttribute("success", "✅ Hoja de vida de " + hojaVida.getNombre() + " guardada correctamente");
            return "redirect:/hojasvida";
            
        } catch (Exception e) {
            // ❌ MENSAJE DE ERROR
            redirectAttributes.addFlashAttribute("error", "❌ Error al guardar: " + e.getMessage());
            return "redirect:/registrarhojavida";
        }
    }
}