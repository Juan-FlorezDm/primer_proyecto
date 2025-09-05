package com.example.primer_proyecto.controllers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.primer_proyecto.model.HojaVida;
import jakarta.validation.Valid;

@Controller
public class GestionHojasController {

    private final JdbcTemplate jdbcTemplate;

    public GestionHojasController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 🎯 MOSTRAR FORMULARIO DE EDICIÓN (ACTUALIZADO)
    @GetMapping("/editar/{numero}")
    public String mostrarFormularioEdicion(@PathVariable String numero, Model model) {
        try {
            String sql = "SELECT nombre, titulo, numero, email, descripcion, experiencia, estudios, habilidades, linkedin, direccion FROM HojaVida WHERE numero = ?";
            
            HojaVida hojaVida = jdbcTemplate.queryForObject(sql, new Object[]{numero}, (rs, rowNum) -> {
                HojaVida hv = new HojaVida();
                hv.setNombre(rs.getString("nombre"));
                hv.setTitulo(rs.getString("titulo"));
                hv.setNumero(rs.getString("numero"));
                hv.setEmail(rs.getString("email"));
                hv.setDescripcion(rs.getString("descripcion"));
                hv.setExperiencia(rs.getString("experiencia"));
                hv.setEstudios(rs.getString("estudios"));
                hv.setHabilidades(rs.getString("habilidades"));
                hv.setLinkedin(rs.getString("linkedin"));
                hv.setDireccion(rs.getString("direccion"));
                return hv;
            });
            
            model.addAttribute("hojaVida", hojaVida);
            model.addAttribute("esEdicion", true);
            return "registrarhojavida";
            
        } catch (Exception e) {
            return "redirect:/hojasvida?error=Hoja+no+encontrada";
        }
    }

    // 🎯 ACTUALIZAR REGISTRO (ACTUALIZADO)
    @PostMapping("/actualizar/{numero}")
    public String actualizarHojaVida(
            @PathVariable String numero,
            @Valid HojaVida hojaVida,
            BindingResult result,
            @RequestParam(value = "pdf", required = false) MultipartFile pdf,
            RedirectAttributes redirectAttributes,
            Model model) {

        // Validar PDF solo si se sube uno nuevo
        if (pdf != null && !pdf.isEmpty()) {
            if (pdf.getSize() > 5242880) {
                result.rejectValue("pdf", "error.pdf", "El PDF no puede exceder 5MB");
            } else if (!pdf.getContentType().equals("application/pdf")) {
                result.rejectValue("pdf", "error.pdf", "Solo se permiten archivos PDF");
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("esEdicion", true);
            return "registrarhojavida";
        }

        try {
            String sql;
            if (pdf != null && !pdf.isEmpty()) {
                // Actualizar con nuevo PDF
                sql = "UPDATE HojaVida SET nombre=?, titulo=?, email=?, descripcion=?, experiencia=?, estudios=?, habilidades=?, linkedin=?, direccion=?, pdf=? WHERE numero=?";
                jdbcTemplate.update(sql, 
                    hojaVida.getNombre(),
                    hojaVida.getTitulo(),
                    hojaVida.getEmail(),
                    hojaVida.getDescripcion(),
                    hojaVida.getExperiencia(),
                    hojaVida.getEstudios(),
                    hojaVida.getHabilidades(),
                    hojaVida.getLinkedin(),
                    hojaVida.getDireccion(),
                    pdf.getBytes(),
                    numero
                );
            } else {
                // Actualizar sin cambiar el PDF
                sql = "UPDATE HojaVida SET nombre=?, titulo=?, email=?, descripcion=?, experiencia=?, estudios=?, habilidades=?, linkedin=?, direccion=? WHERE numero=?";
                jdbcTemplate.update(sql, 
                    hojaVida.getNombre(),
                    hojaVida.getTitulo(),
                    hojaVida.getEmail(),
                    hojaVida.getDescripcion(),
                    hojaVida.getExperiencia(),
                    hojaVida.getEstudios(),
                    hojaVida.getHabilidades(),
                    hojaVida.getLinkedin(),
                    hojaVida.getDireccion(),
                    numero
                );
            }
            
            redirectAttributes.addFlashAttribute("success", 
                "✅ Hoja de vida de " + hojaVida.getNombre() + " actualizada correctamente");
            return "redirect:/hojasvida";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "❌ Error al actualizar: " + e.getMessage());
            return "redirect:/editar/" + numero;
        }
    }
}