package com.LamparasShoop.Repository;

import com.LamparasShoop.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductoId(Long productoId);
    List<Review> findByUsuarioId(Long usuarioId);
}
