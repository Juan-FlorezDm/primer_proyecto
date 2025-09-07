package com.example.primer_proyecto.controllers;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @GetMapping("/registrarhojavida")
    public String mostrarFormulario(Model model) {
        model.addAttribute("hojaVida", new HojaVida());
        
        // 🔹 Agregar lista de países y ciudades
        List<CountryCities> lista = servicioPaises.obtenerPaisesConCiudades();
        model.addAttribute("paises", lista);

        return "registrarhojavida"; // ← tu plantilla Thymeleaf
    }


    @PostMapping("/insertarhojavida")
    public String insertarHojaVida(
            @Valid @ModelAttribute("hojaVida") HojaVida hojaVida,
            @RequestParam ("dialCode") String dialcode,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "registrarhojavida";
        }

        String telefonoCompleto = dialcode + " " + hojaVida.getNumero();

        try {


            jdbcTemplate.update(
                "INSERT INTO HojaVida (nombre, titulo, numero, descripcion, experiencia, estudios, email, linkedin) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                hojaVida.getNombre(),
                hojaVida.getTitulo(),
                telefonoCompleto,
                hojaVida.getDescripcion(),
                hojaVida.getExperiencia(),
                hojaVida.getEstudios(),
                hojaVida.getEmail(),
                hojaVida.getLinkedin()
            );
        } catch (Exception e) {    
            e.printStackTrace();
            model.addAttribute("dbError", e.getMessage());
            return "registrarhojavida";
        }

        return "redirect:/hojasvida";
    }

}
