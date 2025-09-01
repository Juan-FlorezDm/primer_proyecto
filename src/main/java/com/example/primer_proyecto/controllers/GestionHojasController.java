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

    // 🎯 MOSTRAR FORMULARIO DE EDICIÓN
    @GetMapping("/editar/{numero}")
    public String mostrarFormularioEdicion(@PathVariable String numero, Model model) {
        try {
            String sql = "SELECT nombre, numero, descripcion, estudios, email FROM HojaVida WHERE numero = ?";
            HojaVida hojaVida = jdbcTemplate.queryForObject(sql, new Object[]{numero}, (rs, rowNum) -> {
                HojaVida hv = new HojaVida();
                hv.setNombre(rs.getString("nombre"));
                hv.setNumero(rs.getString("numero"));
                hv.setDescripcion(rs.getString("descripcion"));
                hv.setEstudios(rs.getString("estudios"));
                hv.setEmail(rs.getString("email"));
                return hv;
            });
            
            model.addAttribute("hojaVida", hojaVida);
            model.addAttribute("esEdicion", true);
            return "registrarhojavida";
            
        } catch (Exception e) {
            return "redirect:/hojasvida?error=Hoja+no+encontrada";
        }
    }

    // 🎯 ACTUALIZAR REGISTRO (CORREGIDO)
    @PostMapping("/actualizar/{numero}")
    public String actualizarHojaVida(
            @PathVariable String numero,
            @Valid HojaVida hojaVida,
            BindingResult result,
            @RequestParam(value = "pdf", required = false) MultipartFile pdf,
            RedirectAttributes redirectAttributes,
            Model model) { // ✅ AGREGADO Model model

        if (result.hasErrors()) {
            model.addAttribute("esEdicion", true);
            return "registrarhojavida";
        }

        try {
            String sql;
            if (pdf != null && !pdf.isEmpty()) {
                // Actualizar con nuevo PDF
                sql = "UPDATE HojaVida SET nombre = ?, descripcion = ?, estudios = ?, pdf = ?, email = ? WHERE numero = ?";
                jdbcTemplate.update(sql, 
                    hojaVida.getNombre(),
                    hojaVida.getDescripcion(),
                    hojaVida.getEstudios(),
                    pdf.getBytes(),
                    hojaVida.getEmail(),
                    numero);
            } else {
                // Actualizar sin cambiar el PDF
                sql = "UPDATE HojaVida SET nombre = ?, descripcion = ?, estudios = ?, email = ? WHERE numero = ?";
                jdbcTemplate.update(sql, 
                    hojaVida.getNombre(),
                    hojaVida.getDescripcion(),
                    hojaVida.getEstudios(),
                    hojaVida.getEmail(),
                    numero);
            }
            
            redirectAttributes.addFlashAttribute("success", "✅ Hoja de vida de " + hojaVida.getNombre() + " actualizada correctamente");
            return "redirect:/hojasvida";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ Error al actualizar: " + e.getMessage());
            return "redirect:/editar/" + numero;
        }
    }

    // 🗑️ ELIMINAR REGISTRO
    @GetMapping("/eliminar/{numero}")
    public String eliminarHojaVida(@PathVariable String numero, RedirectAttributes redirectAttributes) {
        try {
            String sql = "DELETE FROM HojaVida WHERE numero = ?";
            int filasEliminadas = jdbcTemplate.update(sql, numero);
            
            if (filasEliminadas > 0) {
                redirectAttributes.addFlashAttribute("success", "✅ Hoja de vida eliminada correctamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "❌ No se encontró la hoja de vida para eliminar");
            }
            return "redirect:/hojasvida";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ Error al eliminar: " + e.getMessage());
            return "redirect:/hojasvida";
        }
    }
}