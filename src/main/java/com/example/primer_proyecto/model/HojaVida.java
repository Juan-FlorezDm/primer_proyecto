package com.example.primer_proyecto.model; // usa el paquete que estés usando en tu proyecto


import jakarta.validation.constraints.*;

public class HojaVida {
    @NotBlank(message = "El nombre solo debe contener letras y espacios")
    @Size(max = 50)
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El nombre solo debe contener letras y espacios")
        private String nombre;

        @NotBlank(message = "El número no puede estar vacío")
        @Pattern(
        regexp = "^(?!.*(\\d)\\1{5,})\\+?[1-9]\\d{1,14}$",
        message = "El número debe estar en formato internacional (+57..., +1..., etc.), tener hasta 15 dígitos y no contener más de 5 dígitos iguales consecutivos")
    private String numero;


    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 100, message = "La descripción no puede superar los 100 caracteres")
    @Pattern(
        regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ.,;:()\"'!¡¿?\\s-]+$",
        message = "La descripción solo puede contener letras, números y signos de puntuación básicos")
    private String descripcion;

    @NotBlank(message = "La informacion de estudios no puede estar vacía")
    @Size(max = 100, message = "La descripción no puede superar los 100 caracteres")
    @Pattern(
        regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ.,;:()\"'!¡¿?\\s-]+$",
        message = "La información solo puede contener letras, números y signos de puntuación básicos")
    private String estudios;

    @NotBlank(message = "La información de email no puede estar vacía")
    @Pattern(
    regexp = "^(?!.*(.)\\1{4,})[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
    message = "El correo no puede contener más de 4 caracteres consecutivos iguales y debe ser válido"
)
    private String email;

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstudios() {
        return estudios;
    }
    public void setEstudios(String estudios) {
        this.estudios = estudios;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
