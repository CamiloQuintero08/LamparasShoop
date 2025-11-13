package com.LamparasShoop.Service;

import com.LamparasShoop.Model.Review;
import com.LamparasShoop.Controller.ReviewDTO;
import com.LamparasShoop.Model.Producto;
import com.LamparasShoop.Model.Usuario;
import com.LamparasShoop.Repository.ReviewRepository;
import com.LamparasShoop.Repository.ProductoRepository;
import com.LamparasShoop.Repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    
    private final ReviewRepository reviewRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    
    @Transactional
    public Review crearResena(Long productoId, String username, Integer calificacion, String comentario) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        
        if (calificacion < 1 || calificacion > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }
        
        Review review = new Review();
        review.setCalificacion(calificacion);
        review.setComentario(comentario);
        review.setProducto(producto);
        review.setUsuario(usuario);
        return reviewRepository.save(review);
    }
    
    @Transactional(readOnly = true)
    public List<ReviewDTO> obtenerResenasDelProducto(Long productoId) {
        return reviewRepository.findByProductoId(productoId)
                .stream()
                .map(this::toReviewReq)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public Double obtenerCalificacionPromedio(Long productoId) {
        List<Review> resenas = reviewRepository.findByProductoId(productoId);
        if (resenas.isEmpty()) {
            return 0.0;
        }
        return resenas.stream()
                .mapToInt(Review::getCalificacion)
                .average()
                .orElse(0.0);
    }

    private ReviewDTO toReviewReq(Review review) {
        ReviewDTO reviewReq = new ReviewDTO();
        reviewReq.setUsuario(review.getUsuario().getUsername());
        reviewReq.setComentario(review.getComentario());
        reviewReq.setFecha(review.getFechaCreacion());
        reviewReq.setPuntuacion(review.getCalificacion());
        return reviewReq;
    }
}
