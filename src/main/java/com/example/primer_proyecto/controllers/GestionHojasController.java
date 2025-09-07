package com.example.primer_proyecto.controllers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            String sql = "SELECT nombre, titulo, numero, email, descripcion, experiencia, estudios, habilidades, linkedin FROM HojaVida WHERE numero = ?";
            
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
                return hv;
            });
            
            model.addAttribute("hojaVida", hojaVida);
            model.addAttribute("esEdicion", true);
            return "registrarhojavida";
            
        } catch (Exception e) {
            return "redirect:/hojasvida?error=Hoja+no+encontrada";
        }
    }

    // 🎯 ACTUALIZAR REGISTRO
    @PostMapping("/actualizar/{numero}")
    public String actualizarHojaVida(
            @PathVariable String numero,
            @Valid HojaVida hojaVida,
            BindingResult result,
            @RequestParam ("dialCode") String dialcode,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("esEdicion", true);
            return "registrarhojavida";
        }

        try {
            String sql = "UPDATE HojaVida SET nombre=?, titulo=?, email=?, descripcion=?, experiencia=?, estudios=?, habilidades=?, linkedin=? WHERE numero=?";
            jdbcTemplate.update(sql, 
                hojaVida.getNombre(),
                hojaVida.getTitulo(),
                hojaVida.getEmail(),
                hojaVida.getDescripcion(),
                hojaVida.getExperiencia(),
                hojaVida.getEstudios(),
                hojaVida.getHabilidades(),
                hojaVida.getLinkedin(),
                numero
            );
            
            redirectAttributes.addFlashAttribute("success", 
                "✅ Hoja de vida de " + hojaVida.getNombre() + " actualizada correctamente");
            return "redirect:/hojasvida";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "❌ Error al actualizar: " + e.getMessage());
            return "redirect:/editar/" + numero;
        }
    }

    // 🎯 ELIMINAR REGISTRO
@GetMapping("/eliminar/{numero}")
public String eliminarHojaVida(
        @PathVariable String numero,
        RedirectAttributes redirectAttributes) {
    try {
        String sql = "DELETE FROM HojaVida WHERE numero = ?";
        int filasAfectadas = jdbcTemplate.update(sql, numero);

        if (filasAfectadas > 0) {
            redirectAttributes.addFlashAttribute("success", "✅ Hoja de vida eliminada correctamente");
        } else {
            redirectAttributes.addFlashAttribute("warning", "⚠️ No se encontró la hoja de vida con número: " + numero);
        }
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "❌ Error al eliminar: " + e.getMessage());
    }
    return "redirect:/hojasvida";
}

}
