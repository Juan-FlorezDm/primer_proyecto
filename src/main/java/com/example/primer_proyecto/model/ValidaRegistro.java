package com.example.primer_proyecto.model;

import jakarta.validation.constraints.*;

public class ValidaRegistro {
    @NotBlank(message = "El nombre solo debe contener letras y espacios")
    @Size(max = 50)
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El nombre solo debe contener letras y espacios")
    private String nombre;

    @NotBlank(message = "La información de email no puede estar vacía")
    @Pattern(
    regexp = "^(?!.*(.)\\1{4,})([a-zA-Z0-9._%+-]{1,64})@([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,63}$",
    message = "El correo no es válido o contiene caracteres repetidos en exceso")
    private String email;


    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 14, message = "La contraseña debe tener entre 8 y 14 caracteres")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,14}$",
        message = "La contraseña debe contener al menos un número, una mayúscula, una minúscula y un carácter especial")
    private String password;


    @NotBlank(message = "La cédula es obligatoria")
    @Pattern(regexp = "^[1-9]\\d{2,10}$", message = "La cédula debe tener entre 3 y 10 dígitos y no puede comenzar con 0")
    private String cedula;

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getCedula() {
        return cedula;
    }
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
}
