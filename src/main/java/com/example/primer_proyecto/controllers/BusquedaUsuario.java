package com.example.primer_proyecto.controllers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class BusquedaUsuario {


     private final JdbcTemplate jdbcTemplate;

    public BusquedaUsuario(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int verificarUsuario(String email, String contraseña) {
        String sql = "SELECT COUNT(*) FROM Users WHERE email = ?";
        
        
        if(jdbcTemplate.queryForObject(sql, Integer.class, email) == 0){
            return 1; //usuario no registrado 
        }else{
            String sqlPassword = "SELECT COUNT(*) FROM Users WHERE email = ? AND contraseña = ?";
            int credencialesCorrectas = jdbcTemplate.queryForObject(sqlPassword, Integer.class, email, contraseña);
    
            if (credencialesCorrectas == 1) {
                return 2; // Credenciales correctas
            } else {
                return 3; // Email existe pero contraseña incorrecta
            }
                }
    }

    @PostMapping("/user/busqueda")
    public String postMethodName(@RequestParam ("correo") String email,
                                 @RequestParam ("Contraseña") String contraseña) {
        
        switch (verificarUsuario(email, contraseña)) {
            case 1:
               
                return "redirect:/";
            case 2:
                return "redirect:/consultar_hojas";
            case 3:
                return "redirect:/";
            default:
                return "redirect:/";
            
        }
    }
   
    

}
