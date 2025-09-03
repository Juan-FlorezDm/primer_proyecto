package com.example.primer_proyecto.Servicios;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.primer_proyecto.DTO.CountriesResponse;
import com.example.primer_proyecto.DTO.CountryCities;
import com.example.primer_proyecto.DTO.CountryCode;
import com.example.primer_proyecto.DTO.CountryCodesResponse;


@Service
public class ServicioPaises {
    private final WebClient webClient;

    public ServicioPaises(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<CountryCities> obtenerPaisesConCiudades() {
        CountriesResponse response = webClient.get()
            .uri("/countries")
            .retrieve()
            .bodyToMono(CountriesResponse.class)
            .block();

        return response != null ? response.data() : List.of();
    }

    public List<String> obtenerCiudadesPorPais(String pais) {
        return obtenerPaisesConCiudades().stream()
            .filter(cc -> cc.country().equalsIgnoreCase(pais))
            .findFirst()
            .map(CountryCities::cities)
            .orElse(List.of());
    }

public String obtenerDialCodePorPais(String pais) {
    CountryCodesResponse response = webClient.get()
        .uri("/countries/codes")
        .retrieve()
        .bodyToMono(CountryCodesResponse.class)
        .block();

    return response.data().stream()
        .filter(cc -> cc.name().equalsIgnoreCase(pais))
        .findFirst()
        .map(CountryCode::dial_code)
        .orElse("NO ENCONTRADO");
}
}