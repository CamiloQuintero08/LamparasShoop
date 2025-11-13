package com.LamparasShoop.Controller;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private String usuario;
    private String comentario;
    private Integer puntuacion;
    private LocalDate fecha;
}
