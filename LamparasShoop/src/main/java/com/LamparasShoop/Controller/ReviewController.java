package com.LamparasShoop.Controller;

import com.LamparasShoop.Model.Review;
import com.LamparasShoop.Service.ReviewService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
@RequiredArgsConstructor
public class ReviewController {
    
    private final ReviewService reviewService;
    
    @PostMapping("/{idProducto}")
    public ResponseEntity<Review> crearResena(@PathVariable Long idProducto, @RequestBody ReviewDTO req) {
            Review review = reviewService.crearResena(idProducto, req.getUsuario(), req.getPuntuacion(), req.getComentario());
            return new ResponseEntity<>(review, HttpStatus.CREATED);
    }
    
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<ReviewDTO>> obtenerResenasDelProducto(@PathVariable Long productoId) {
        List<ReviewDTO> resenas = reviewService.obtenerResenasDelProducto(productoId);
        return new ResponseEntity<>(resenas, HttpStatus.OK);
    }
    
    @GetMapping("/producto/{productoId}/promedio")
    public ResponseEntity<Double> obtenerCalificacionPromedio(@PathVariable Long productoId) {
        Double promedio = reviewService.obtenerCalificacionPromedio(productoId);
        return new ResponseEntity<>(promedio, HttpStatus.OK);
    }
}
