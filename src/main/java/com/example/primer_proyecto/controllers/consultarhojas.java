package com.example.primer_proyecto.controllers;

import java.util.List;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.primer_proyecto.model.HojaVida;

@Controller
public class consultarhojas {


     private JdbcTemplate jdbcTemplate;

    public consultarhojas(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @GetMapping("/hojasvida")
    public String listarHojasVida(Model model) {
        String sql = "SELECT nombre, numero, descripcion, estudios FROM HojaVida";
        List<HojaVida> lista = jdbcTemplate.query(sql, (rs, rowNum) -> {
            HojaVida hv = new HojaVida();
            hv.setNombre(rs.getString("nombre"));
            hv.setNumero(rs.getString("numero"));
            hv.setDescripcion(rs.getString("descripcion"));
            hv.setEstudios(rs.getString("estudios"));
            return hv;
        });

        model.addAttribute("hojas", lista);
        return "consultar_hojas"; 
    }


@GetMapping("/hojasvida/pdf/{numero}")
    public ResponseEntity<byte[]> descargarPdf(@PathVariable String numero,
                                               @RequestParam(defaultValue = "false") boolean ver) {
        String sql = "SELECT nombre, pdf FROM HojaVida WHERE numero = ?";
        return jdbcTemplate.query(sql, ps -> ps.setString(1, numero), rs -> {
            if (rs.next()) {
                String nombreArchivo = rs.getString("nombre") + ".pdf";
                byte[] pdfBytes = rs.getBytes("pdf");

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);

                if (ver) {
                    headers.setContentDisposition(ContentDisposition.builder("inline").filename(nombreArchivo).build());
                } else {
                    headers.setContentDisposition(ContentDisposition.builder("attachment").filename(nombreArchivo).build());
                }

                return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
        });
    }


}