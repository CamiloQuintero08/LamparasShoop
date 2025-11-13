package com.LamparasShoop.Service;

import com.LamparasShoop.Model.Review;
import com.LamparasShoop.Model.Producto;
import com.LamparasShoop.Model.Usuario;
import com.LamparasShoop.Repository.ReviewRepository;
import com.LamparasShoop.Repository.ProductoRepository;
import com.LamparasShoop.Repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public List<Review> obtenerResenasDelProducto(Long productoId) {
        return reviewRepository.findByProductoId(productoId);
    }
    
    @Transactional(readOnly = true)
    public List<Review> obtenerResenasDelUsuario(Long usuarioId) {
        return reviewRepository.findByUsuarioId(usuarioId);
    }
    
    @Transactional(readOnly = true)
    public Optional<Review> obtenerResena(Long id) {
        return reviewRepository.findById(id);
    }
    
    @Transactional
    public Review actualizarResena(Long id, Integer calificacion, String comentario) {
        Optional<Review> review = reviewRepository.findById(id);
        
        if (review.isEmpty()) {
            throw new IllegalArgumentException("Reseña no encontrada");
        }
        
        if (calificacion < 1 || calificacion > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }
        
        Review resenaActualizar = review.get();
        resenaActualizar.setCalificacion(calificacion);
        resenaActualizar.setComentario(comentario);
        
        return reviewRepository.save(resenaActualizar);
    }

    @Transactional
    public void eliminarResena(Long id) {
        reviewRepository.deleteById(id);
    }
    
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
}
