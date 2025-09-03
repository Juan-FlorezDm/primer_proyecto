package com.example.primer_proyecto.controllers;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.primer_proyecto.DTO.CountryCities;
import com.example.primer_proyecto.Servicios.ServicioPaises;
import com.example.primer_proyecto.model.HojaVida;


import jakarta.validation.Valid;

@Controller
public class insertarhojavida { 
    private final JdbcTemplate jdbcTemplate;
    private final ServicioPaises servicioPaises;

    public insertarhojavida(JdbcTemplate jdbcTemplate, ServicioPaises servicioPaises) {
        this.jdbcTemplate = jdbcTemplate;
        this.servicioPaises = servicioPaises;
    }

    // 👉 Mostrar formulario de registro con países cargados
    @GetMapping("/registrarhojavida")
    public String mostrarFormulario(Model model) {
        model.addAttribute("hojaVida", new HojaVida());
        
        // 🔹 Agregar lista de países y ciudades
        List<CountryCities> lista = servicioPaises.obtenerPaisesConCiudades();
        model.addAttribute("paises", lista);

        return "registrarhojavida"; // ← tu plantilla Thymeleaf
    }

    // 👉 Guardar Hoja de Vida
   @PostMapping("/insertarhojavida")
public String saveUser(
        @Valid HojaVida hojaVida,
        BindingResult result,
        @RequestParam("pdf") MultipartFile pdf,
        @RequestParam("dialCode") String dialCode, // 👈 capturamos el prefijo
        RedirectAttributes redirectAttributes,
        Model model) {

    if (result.hasErrors()) {
        model.addAttribute("paises", servicioPaises.obtenerPaisesConCiudades());
        return "registrarhojavida";
    }

    try {
        // Concatenamos dial + número
        String numeroCompleto = dialCode + hojaVida.getNumero();

        String sql = "INSERT INTO HojaVida (nombre, numero, descripcion, estudios, pdf, email) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                hojaVida.getNombre(),
                numeroCompleto,
                hojaVida.getDescripcion(),
                hojaVida.getEstudios(),
                pdf.getBytes(),
                hojaVida.getEmail()
        );

        redirectAttributes.addFlashAttribute("success",
            "✅ Hoja de vida de " + hojaVida.getNombre() + " guardada correctamente");
        return "redirect:/hojasvida";
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "❌ Error al guardar: " + e.getMessage());
        return "redirect:/registrarhojavida";
    }
}

}
