package com.LamparasShoop.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LamparasShoop.Model.Producto;
import com.LamparasShoop.Repository.ProductoRepository;

@Service
public class ChatService {

    @Autowired
    private ProductoRepository productoRepository;

    public String procesarMensaje(String mensajeUsuario) {
        String mensaje = mensajeUsuario.toLowerCase();
        StringBuilder respuesta = new StringBuilder();

        if (mensaje.contains("hola") || mensaje.contains("buenos")) {
            return "¡Hola! Bienvenid@ a Aura Artesanal. Soy tu asistente virtual. ¿Te gustaría ver lámparas, macetas y relojes o recibir informacion acerca del proyecto?";
        } 
        else if (mensaje.contains("gracias") || mensaje.contains("adiós")) {
            return "¡Gracias por visitar Aura Artesanal! Si necesitas algo más, no dudes en volver.";
        }
        else if (mensaje.contains("proyecto") || mensaje.contains("aura artesanal")) {
            return "Aura Artesanal es un proyecto dedicado a ofrecer productos hechos a mano con amor y dedicación. ¡Gracias por tu interés!";
        }
        else if (mensaje.contains("ver productos") || mensaje.contains("productos")) {
            respuesta.append("Claro, aquí tienes algunas de nuestras categorías populares:\n");
            respuesta.append("• Lámparas\n");
            respuesta.append("• Macetas\n");
            respuesta.append("• Relojes\n");
            respuesta.append("¿Cuál te gustaría explorar?");
        }
        else if (mensaje.contains("lampara")) {
            respuesta.append("Aquí tienes nuestras lámparas artesanales:\n");
            appendProductos(respuesta, "LAMPARA");
        } 
        else if (mensaje.contains("maceta")) {
            respuesta.append("Mira estas hermosas macetas:\n");
            appendProductos(respuesta, "MACETA");
        } 
        else if (mensaje.contains("reloj")) {
            respuesta.append("El tiempo con estilo. Nuestros relojes:\n");
            appendProductos(respuesta, "RELOJ");
        } 
        else {
            return "Lo siento, no entendí tu consulta. Prueba escribiendo 'lámparas', 'macetas' o 'relojes'.";
        }

        return respuesta.toString();
    }

    private void appendProductos(StringBuilder sb, String keyword) {
        List<Producto> productos = productoRepository.findAll().stream()
            .filter(p -> p.getCategoria().name().toLowerCase().contains(keyword.toLowerCase()) ||
                         p.getNombre().toLowerCase().contains(keyword.toLowerCase()))
            .toList();

        if (productos.isEmpty()) {
            sb.append("Lo siento, no tenemos stock de eso en este momento.");
        } else {
            for (Producto p : productos) {
                sb.append("• ").append(p.getNombre())
                  .append(" ($").append(p.getPrecio())
                  .append(") - Stock: ").append(p.getStock()).append("\n");
            }
        }
    }
}
