package com.example.primer_proyecto.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public record pais(
        String name,
        @JsonProperty("Iso2") String iso2,
        @JsonProperty("Iso3") String iso3
) {}
