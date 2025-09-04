package com.example.primer_proyecto.controllers;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.primer_proyecto.model.HojaVida;

@Controller
public class consultarhojas {

    private final JdbcTemplate jdbcTemplate;

    public consultarhojas(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/hojasvida")
    public String listarHojasVida(Model model) {
        try {
            String sql = "SELECT nombre, titulo, numero, email, descripcion, experiencia, estudios, habilidades FROM HojaVida";
            
            List<HojaVida> lista = jdbcTemplate.query(sql, (rs, rowNum) -> {
                HojaVida hv = new HojaVida();
                hv.setNombre(rs.getString("nombre"));
                hv.setTitulo(rs.getString("titulo"));
                hv.setNumero(rs.getString("numero"));
                hv.setEmail(rs.getString("email"));
                hv.setDescripcion(rs.getString("descripcion"));
                hv.setExperiencia(rs.getString("experiencia"));
                hv.setEstudios(rs.getString("estudios"));
                hv.setHabilidades(rs.getString("habilidades"));
                return hv;
            });

            model.addAttribute("hojas", lista);
            return "consultar_hojas";
            
        } catch (Exception e) {
            model.addAttribute("error", "❌ Error al cargar las hojas de vida: " + e.getMessage());
            return "consultar_hojas";
        }
    }
}