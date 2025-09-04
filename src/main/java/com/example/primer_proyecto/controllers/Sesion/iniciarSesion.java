package com.example.primer_proyecto.controllers.Sesion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class iniciarSesion {

     @GetMapping("/iniciarSesion")
    public String mostrarLogin() {
        return "iniciarSesion"; 
    }
}
