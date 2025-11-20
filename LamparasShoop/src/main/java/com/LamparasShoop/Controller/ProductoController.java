package com.LamparasShoop.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.LamparasShoop.Model.Producto;
import com.LamparasShoop.Model.Usuario;
import com.LamparasShoop.Repository.ProductoRepository;
import com.LamparasShoop.Repository.ReviewRepository;
import com.LamparasShoop.Repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoRepository productoRepository;
    private final ReviewRepository reviewRepository;
    private final UsuarioRepository usuarioRepository;


    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "productos/lista";
    }

    
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new Producto());
        return "productos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto,
                          @RequestParam MultipartFile imagenFile) throws IOException {

        if (!imagenFile.isEmpty()) {
            producto.setImagen(imagenFile.getBytes());
        }

        if (producto.getCategoria() == null) {
            throw new RuntimeException("La categoria es requerida");
        }
        
        productoRepository.save(producto);
        return "redirect:/productos";
    }


    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        model.addAttribute("producto", producto);
        return "productos/formulario";
    }

    
    @Transactional
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        reviewRepository.deleteAllByProducto(producto);
        productoRepository.delete(producto);
        return "redirect:/productos";
    }

    
    @GetMapping("/catalogo")
    public String catalogo(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String username = auth.getName();
            Usuario usuario = usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
            model.addAttribute("usuario", usuario);
        } else {
            model.addAttribute("usuario", null);
        }
        model.addAttribute("productos", productoRepository.findAll());
        return "index"; // tu vista principal
    }

    
    @GetMapping("/api")
    @ResponseBody
    public List<Producto> obtenerProductosJson() {
        return productoRepository.findAll();
    }

}


