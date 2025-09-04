package com.example.primer_proyecto.model;

import jakarta.validation.constraints.*;

public class HojaVida {
    // VALIDACIÓN DE NOMBRE (MÁS ROBUSTA)
    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Pattern(
        regexp = "^(?!.*([A-Za-z])\\1{2,})[A-Za-záéíóúÁÉÍÓÚñÑüÜ\\s]{2,50}$",
        message = "El nombre solo puede contener letras y espacios, sin caracteres repetidos en exceso"
    )
    private String nombre;

    // VALIDACIÓN DE TÍTULO PROFESIONAL (NUEVO)
    @NotBlank(message = "El título profesional es obligatorio")
    @Size(min = 2, max = 80, message = "El título debe tener entre 2 y 80 caracteres")
    @Pattern(
        regexp = "^(?!.*(.)\\1{3,})[A-Za-záéíóúÁÉÍÓÚñÑüÜ0-9\\s\\-\\.,\\(\\)]{2,80}$",
        message = "El título contiene caracteres no válidos o repeticiones excesivas"
    )
    private String titulo;

    // VALIDACIÓN DE TELÉFONO (MEJORADA)
    @NotBlank(message = "El número de teléfono es obligatorio")
    @Pattern(
        regexp = "^(\\+?57)?[1-9]\\d{1,14}$",
        message = "El número debe ser válido para Colombia (ej: +573001234567 o 3001234567)"
    )
    private String numero;

    // VALIDACIÓN DE EMAIL (MÁS SEGURA)
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Debe ser una dirección de correo electrónico válida")
    @Pattern(
        regexp = "^(?!.*([._%+-])\\1)(?!.*(@)[@\\s])([a-zA-Z0-9._%+-]{1,64})@([a-zA-Z0-9.-]{1,255})\\.([a-zA-Z]{2,63})$",
        message = "Formato de correo no válido o contiene caracteres repetidos"
    )
    @Size(max = 320, message = "El correo no puede exceder 320 caracteres")
    private String email;

    // VALIDACIÓN DE DESCRIPCIÓN (MEJORADA)
    @NotBlank(message = "La descripción profesional es obligatoria")
    @Size(min = 10, max = 500, message = "La descripción debe tener entre 10 y 500 caracteres")
    @Pattern(
        regexp = "^(?!.*(.)\\1{4,})[A-Za-záéíóúÁÉÍÓÚñÑüÜ0-9\\s\\-\\.,;:!¡¿?\\\"'\\+\\(\\)]{10,500}$",
        message = "La descripción contiene caracteres no válidos o repeticiones excesivas"
    )
    private String descripcion;

    // VALIDACIÓN DE EXPERIENCIA (NUEVO)
    @NotBlank(message = "La experiencia laboral es obligatoria")
    @Size(min = 10, max = 1000, message = "La experiencia debe tener entre 10 y 1000 caracteres")
    @Pattern(
        regexp = "^(?!.*(.)\\1{5,})[A-Za-záéíóúÁÉÍÓÚñÑüÜ0-9\\s\\-\\.,;:!¡¿?\\\"'\\+\\(\\)\\/]{10,1000}$",
        message = "La experiencia contiene caracteres no válidos"
    )
    private String experiencia;

    // VALIDACIÓN DE ESTUDIOS (MEJORADA)
    @NotBlank(message = "La información de estudios es obligatoria")
    @Size(min = 5, max = 500, message = "Los estudios deben tener entre 5 y 500 caracteres")
    @Pattern(
        regexp = "^(?!.*(.)\\1{4,})[A-Za-záéíóúÁÉÍÓÚñÑüÜ0-9\\s\\-\\.,;:!¡¿?\\\"'\\+\\(\\)]{5,500}$",
        message = "La información de estudios contiene caracteres no válidos"
    )
    private String estudios;

    // VALIDACIÓN DE HABILIDADES (NUEVO)
    @NotBlank(message = "Las habilidades son obligatorias")
    @Size(min = 2, max = 300, message = "Las habilidades deben tener entre 2 y 300 caracteres")
    @Pattern(
        regexp = "^(?!.*(,)\\1)[A-Za-záéíóúÁÉÍÓÚñÑüÜ0-9\\s\\-\\+\\(\\)#,]{2,300}$",
        message = "Las habilidades contienen caracteres no válidos"
    )
    private String habilidades;

    // CAMPO OPCIONAL - LINKEDIN
    @Size(max = 100, message = "El enlace de LinkedIn no puede exceder 100 caracteres")
    @Pattern(
        regexp = "^(|https?:\\/\\/(www\\.)?linkedin\\.com\\/in\\/[A-Za-z0-9\\-._]{5,100})$",
        message = "El enlace de LinkedIn debe ser válido"
    )
    private String linkedin;

    // CAMPO OPCIONAL - DIRECCIÓN
    @Size(max = 150, message = "La dirección no puede exceder 150 caracteres")
    @Pattern(
        regexp = "^(|[A-Za-záéíóúÁÉÍÓÚñÑüÜ0-9\\s\\-\\.,#]{5,150})$",
        message = "La dirección contiene caracteres no válidos"
    )
    private String direccion;

    // ARCHIVO PDF (validado en el controller)
    private byte[] pdf;

    // GETTERS Y SETTERS
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getExperiencia() { return experiencia; }
    public void setExperiencia(String experiencia) { this.experiencia = experiencia; }

    public String getEstudios() { return estudios; }
    public void setEstudios(String estudios) { this.estudios = estudios; }

    public String getHabilidades() { return habilidades; }
    public void setHabilidades(String habilidades) { this.habilidades = habilidades; }

    public String getLinkedin() { return linkedin; }
    public void setLinkedin(String linkedin) { this.linkedin = linkedin; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public byte[] getPdf() { return pdf; }
    public void setPdf(byte[] pdf) { this.pdf = pdf; }
}