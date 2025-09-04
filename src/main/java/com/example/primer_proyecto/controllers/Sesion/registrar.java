package com.example.primer_proyecto.controllers.Sesion;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.primer_proyecto.Servicios.SeguridadContraseñas;
import com.example.primer_proyecto.model.ValidaRegistro;

import jakarta.validation.Valid;


@Controller
public class registrar {

    private final JdbcTemplate jdbcTemplate;
    private final SeguridadContraseñas seguridadContraseñas;
    
    public registrar(JdbcTemplate jdbcTemplate, SeguridadContraseñas seguridadContraseñas) {
        this.seguridadContraseñas = seguridadContraseñas;
        this.jdbcTemplate = jdbcTemplate;
    }
// Mostrar el formulario
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("validaRegistro", new ValidaRegistro());
        return "registro";
    }

    // Procesar el formulario
    @PostMapping("/insertarusuario")
    public String registrarUsuario(
            @Valid ValidaRegistro validaRegistro,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            // ⚠️ Si hay errores de validación, vuelve al formulario con mensajes
            return "registro";
        }

        try {
            String sql = "INSERT INTO usuarios (nombre, cedula, email, contrasena) VALUES (?, ?, ?, ?)";
            String contrasenaCifrada = seguridadContraseñas.cifrarContraseña(validaRegistro.getPassword());

            jdbcTemplate.update(sql,
                    validaRegistro.getNombre(),
                    validaRegistro.getCedula(),
                    validaRegistro.getEmail(),
                    contrasenaCifrada);

            model.addAttribute("success", "Usuario registrado con éxito ✅");
            return "registroexitoso"; // Vista de éxito

        } catch (Exception e) {
            model.addAttribute("error", "❌ Error al registrar el usuario: " + e.getMessage());
            return "registro";
        }
    }
}
