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

    @GetMapping("/registrarhojavida")
    public String mostrarFormulario(Model model) {
        model.addAttribute("hojaVida", new HojaVida());
        
        // 🔹 Agregar lista de países y ciudades
        List<CountryCities> lista = servicioPaises.obtenerPaisesConCiudades();
        model.addAttribute("paises", lista);

        return "registrarhojavida"; // ← tu plantilla Thymeleaf
    }

   @PostMapping("/insertarhojavida")
    public String saveUser(
            @Valid HojaVida hojaVida,
            BindingResult result,
            @RequestParam("pdf") MultipartFile pdf,
            @RequestParam("dialCode") String dialCode, // 👈 capturamos el prefijo
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
            model.addAttribute("paises", servicioPaises.obtenerPaisesConCiudades());
            return "registrarhojavida";
        }

            try {
                String numeroCompleto = dialCode + hojaVida.getNumero();

                byte[] pdfBytes = null;
                if (!pdf.isEmpty()) {
                    pdfBytes = pdf.getBytes();
                }

                String sql = "INSERT INTO HojaVida (nombre, titulo, numero, descripcion, experiencia, estudios, habilidades, pdf, email, linkedin, direccion) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                jdbcTemplate.update(sql,
                        hojaVida.getNombre(),
                        hojaVida.getTitulo(),
                        numeroCompleto,
                        hojaVida.getDescripcion(),
                        hojaVida.getExperiencia(),
                        hojaVida.getEstudios(),
                        hojaVida.getHabilidades(),
                        pdfBytes,  // 👈 puede ir NULL si no se subió archivo
                        hojaVida.getEmail(),
                        hojaVida.getLinkedin(),
                        hojaVida.getDireccion()
                );


                redirectAttributes.addFlashAttribute("success",
                    "✅ Hoja de vida de " + hojaVida.getNombre() + " guardada correctamente");

                return "redirect:/hojasvida";

            } catch (Exception e) {

                e.printStackTrace();
                redirectAttributes.addFlashAttribute("error",
                    "❌ Error al guardar: " + e.getMessage());
                return "redirect:/registrarhojavida";
            }

    }
}
