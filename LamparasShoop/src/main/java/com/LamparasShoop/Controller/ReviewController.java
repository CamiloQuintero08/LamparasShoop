package com.LamparasShoop.Controller;

import com.LamparasShoop.Model.Review;
import com.LamparasShoop.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/resenas")
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;
    
    @PostMapping("/{idProducto}")
    public ResponseEntity<Review> crearResena(@PathVariable Long idProducto, @RequestBody ReviewReq req) {
            Review review = reviewService.crearResena(idProducto, req.getUsuario(), req.getPuntuacion(), req.getComentario());
            return new ResponseEntity<>(review, HttpStatus.CREATED);
    }
    
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<Review>> obtenerResenasDelProducto(@PathVariable Long productoId) {
        List<Review> resenas = reviewService.obtenerResenasDelProducto(productoId);
        return new ResponseEntity<>(resenas, HttpStatus.OK);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Review>> obtenerResenasDelUsuario(@PathVariable Long usuarioId) {
        List<Review> resenas = reviewService.obtenerResenasDelUsuario(usuarioId);
        return new ResponseEntity<>(resenas, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Review> obtenerResena(@PathVariable Long id) {
        Optional<Review> review = reviewService.obtenerResena(id);
        if (review.isPresent()) {
            return new ResponseEntity<>(review.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Review> actualizarResena(
            @PathVariable Long id,
            @RequestParam Integer calificacion,
            @RequestParam(required = false) String comentario) {
        try {
            Review review = reviewService.actualizarResena(id, calificacion, comentario);
            return new ResponseEntity<>(review, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarResena(@PathVariable Long id) {
        reviewService.eliminarResena(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/producto/{productoId}/promedio")
    public ResponseEntity<Double> obtenerCalificacionPromedio(@PathVariable Long productoId) {
        Double promedio = reviewService.obtenerCalificacionPromedio(productoId);
        return new ResponseEntity<>(promedio, HttpStatus.OK);
    }
}
