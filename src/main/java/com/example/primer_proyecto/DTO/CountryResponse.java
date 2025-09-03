package com.example.primer_proyecto.DTO;


import java.util.List;

public record CountryResponse(
        boolean error,
        String msg,
        List<pais> data
) {}
