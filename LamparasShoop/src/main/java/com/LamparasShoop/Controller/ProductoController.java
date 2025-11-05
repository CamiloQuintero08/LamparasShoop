package com.LamparasShoop.Controller;

import com.LamparasShoop.Model.Producto;
import com.LamparasShoop.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // 📋 LISTAR PRODUCTOS
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "productos/lista";
    }

    // ➕ FORMULARIO NUEVO PRODUCTO
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


    // ✏️ EDITAR PRODUCTO
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        model.addAttribute("producto", producto);
        return "productos/formulario";
    }

    // 🗑️ ELIMINAR PRODUCTO
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        productoRepository.deleteById(id);
        return "redirect:/productos";
    }


    // 🏠 MOSTRAR PRODUCTOS EN EL INDEX
    @GetMapping("/catalogo")
    public String catalogo(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "index"; // tu vista principal
    }

    // ✅ API: devolver productos como JSON para el catálogo dinámico
    @GetMapping("/api")
    @ResponseBody
    public List<Producto> obtenerProductosJson() {
        return productoRepository.findAll();
    }

}


