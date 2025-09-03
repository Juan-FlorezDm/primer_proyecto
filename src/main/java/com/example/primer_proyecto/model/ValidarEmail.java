package com.example.primer_proyecto.model;

import jakarta.validation.constraints.*;
public class ValidarEmail {
    
    @NotBlank(message = "La información de email no puede estar vacía")
    @Pattern(
    regexp = "^(?!.*(.)\\1{4,})[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
    message = "El correo no puede contener más de 4 caracteres consecutivos iguales y debe ser válido")
    private String email;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
