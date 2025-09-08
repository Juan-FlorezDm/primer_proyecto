package com.example.primer_proyecto.Servicios;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

@Service
public class SeguridadContraseñas {
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String cifrarContraseña(String contraseña) {
        return passwordEncoder.encode(contraseña);
    }

    public boolean verificarContraseña(String contraseña, String contraseñaCifrada) {
        return passwordEncoder.matches(contraseña, contraseñaCifrada);
    }

}
