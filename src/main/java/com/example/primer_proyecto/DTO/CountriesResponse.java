package com.example.primer_proyecto.DTO;

import java.util.List;

public record CountriesResponse(
    boolean error,
    String msg,
    List<CountryCities> data
) {}
