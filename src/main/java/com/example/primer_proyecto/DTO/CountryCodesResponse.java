package com.example.primer_proyecto.DTO;

import java.util.List;

public record CountryCodesResponse(
    boolean error,
    String msg,
    List<CountryCode> data
) {}
