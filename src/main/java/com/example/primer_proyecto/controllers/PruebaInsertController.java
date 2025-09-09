package com.example.primer_proyecto.controllers;

import com.example.primer_proyecto.model.HojaVida;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PruebaInsertController {

    private final JdbcTemplate jdbcTemplate;

    public PruebaInsertController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/pruebainsert")
    public String mostrarForm(Model model) {
        if (!model.containsAttribute("hojaVida")) {
            model.addAttribute("hojaVida", new HojaVida());
        }
        return "pruebainsert";
    }

    @PostMapping("/pruebainsert_data")
    public String insertarDatos(@ModelAttribute HojaVida hojaVida,
                            @RequestParam(value = "pdf", required = false) MultipartFile pdfFile,
                            @RequestParam(value = "dialCode", required = false) String dialCode,
                            RedirectAttributes redirectAttributes) {
    try {
        // Concatenar el prefijo con el número de teléfono
        String numeroCompleto = (dialCode != null ? dialCode : "") + hojaVida.getNumero();
        hojaVida.setNumero(numeroCompleto);

    

        // Insertar en la base de datos
        String sql = "INSERT INTO HojaVida(nombre, titulo, numero, descripcion, experiencia, estudios, habilidades, pdf, email, linkedin, direccion) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
            hojaVida.getNombre(),
            hojaVida.getTitulo(),
            hojaVida.getNumero(),
            hojaVida.getDescripcion(),
            hojaVida.getExperiencia(),
            hojaVida.getEstudios(),
            hojaVida.getHabilidades(),
            hojaVida.getPdf(),
            hojaVida.getEmail(),
            hojaVida.getLinkedin(),
            hojaVida.getDireccion()
        );

        redirectAttributes.addFlashAttribute("mensaje", "Hoja de vida guardada exitosamente!");
        return "redirect:/pruebainsert";

    } catch (Exception e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("error", "Error al guardar la hoja de vida: " + e.getMessage());
        redirectAttributes.addFlashAttribute("hojaVida", hojaVida);
        return "redirect:/pruebainsert";
    }
}

}
