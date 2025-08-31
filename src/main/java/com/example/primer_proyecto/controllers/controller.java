package com.example.primer_proyecto.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.primer_proyecto.model.HojaVida;

@Controller
public class controller {

  @GetMapping("/registrarhojavida")
    public String mostrarFormulario(Model model) {
        model.addAttribute("hojaVida", new HojaVida());
        return "registrarhojavida";
    }

}
