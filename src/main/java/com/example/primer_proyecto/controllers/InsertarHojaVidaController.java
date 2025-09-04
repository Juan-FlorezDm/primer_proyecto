package com.example.primer_proyecto.controllers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.primer_proyecto.model.HojaVida;
import jakarta.validation.Valid;

@Controller
public class InsertarHojaVidaController {

    private final JdbcTemplate jdbcTemplate;

    public InsertarHojaVidaController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 👉 MOSTRAR FORMULARIO
    @GetMapping("/registrarhojavida")
    public String mostrarFormulario(Model model) {
        model.addAttribute("hojaVida", new HojaVida());
        return "registrarhojavida";
    }

    // 👉 GUARDAR HOJA DE VIDA (ACTUALIZADO)
    @PostMapping("/insertarhojavida")
    public String guardarHojaVida(
            @Valid HojaVida hojaVida,
            BindingResult result,
            @RequestParam("pdf") MultipartFile pdf,
            RedirectAttributes redirectAttributes,
            Model model) {

        // Validar archivo PDF
        if (pdf.isEmpty()) {
            result.rejectValue("pdf", "error.pdf", "El archivo PDF es requerido");
        } else if (pdf.getSize() > 5242880) { // 5MB en bytes
            result.rejectValue("pdf", "error.pdf", "El PDF no puede exceder 5MB");
        } else if (!pdf.getContentType().equals("application/pdf")) {
            result.rejectValue("pdf", "error.pdf", "Solo se permiten archivos PDF");
        }

        if (result.hasErrors()) {
            return "registrarhojavida";
        }

        try {
            String sql = "INSERT INTO HojaVida (nombre, titulo, numero, email, descripcion, experiencia, estudios, habilidades, linkedin, direccion, pdf) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            jdbcTemplate.update(sql,
                hojaVida.getNombre(),
                hojaVida.getTitulo(),
                hojaVida.getNumero(),
                hojaVida.getEmail(),
                hojaVida.getDescripcion(),
                hojaVida.getExperiencia(),
                hojaVida.getEstudios(),
                hojaVida.getHabilidades(),
                hojaVida.getLinkedin(),
                hojaVida.getDireccion(),
                pdf.getBytes()
            );

            redirectAttributes.addFlashAttribute("success",
                "✅ Hoja de vida de " + hojaVida.getNombre() + " guardada correctamente");
            return "redirect:/hojasvida";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "❌ Error al guardar: " + e.getMessage());
            return "redirect:/registrarhojavida";
        }
    }
}