package com.example.primer_proyecto.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controller {

    @GetMapping("/registrar_hoja_vida")
    public String registrarHojaVida() {
        return "registrar_hoja_vida";
    }

    @GetMapping("/consultar_hojas")
    public String consultarHojas() {
        return "consultar_hojas";
    }
    
    @GetMapping("/Registrarusuario")
    public String registrarUsuario() {
        return "Registrarusuario";
    }
}
