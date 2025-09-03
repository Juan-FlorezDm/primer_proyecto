package com.example.primer_proyecto.DTO;
import java.util.List;

public record CountryCities(
    String country,
    String iso2,
    String iso3,
    String dialCode,
    List<String> cities
) {}

