package com.LamparasShoop.Controller;

import com.LamparasShoop.Model.Producto;
import com.LamparasShoop.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    private final String UPLOAD_DIR = "src/main/resources/static/img/";

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
                          @RequestParam("imagenFile") MultipartFile imagenFile) throws IOException {

        if (!imagenFile.isEmpty()) {
            String nombreArchivo = UUID.randomUUID().toString() + "_" + imagenFile.getOriginalFilename();

            Path directorioImagenes = Paths.get("src/main/resources/static/img/");
            if (!Files.exists(directorioImagenes)) {
                Files.createDirectories(directorioImagenes); // ✅ crea la carpeta si no existe
            }

            Path ruta = directorioImagenes.resolve(nombreArchivo);
            Files.write(ruta, imagenFile.getBytes());

            producto.setImagenUrl("/img/" + nombreArchivo);
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


