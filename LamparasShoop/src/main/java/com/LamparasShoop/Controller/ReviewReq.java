package com.LamparasShoop.Controller;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReviewReq {
  String usuario;
  String comentario;
  Integer puntuacion;
  LocalDate fecha;
}
