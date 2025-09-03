package com.example.primer_proyecto.controllers.formulario;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.primer_proyecto.DTO.CountryCities;
import com.example.primer_proyecto.Servicios.ServicioPaises;


@Controller
@RequestMapping("/formulario")
public class control {

    private final ServicioPaises servicioPaises;

    public control(ServicioPaises servicioPaises) {
        this.servicioPaises = servicioPaises;
    }

    @GetMapping
    public String verform(Model model) {
        List<CountryCities> lista = servicioPaises.obtenerPaisesConCiudades();
        model.addAttribute("paises", lista);
        return "index";
    }

    @GetMapping("/cuidades")
    @ResponseBody
    public List<String> obtenerCiudades(@RequestParam String pais) {
        return servicioPaises.obtenerCiudadesPorPais(pais);
    }

   @GetMapping("/dialcode")
    @ResponseBody
    public String obtenerDialCode(@RequestParam String pais) {
        return servicioPaises.obtenerDialCodePorPais(pais);
    }


}


