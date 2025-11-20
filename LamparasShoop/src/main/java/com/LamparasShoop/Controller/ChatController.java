package com.LamparasShoop.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.LamparasShoop.Service.ChatService;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public Map<String, String> chatear(@RequestBody Map<String, String> payload) {
        String mensajeUsuario = payload.get("mensaje");
        String respuestaBot = chatService.procesarMensaje(mensajeUsuario);
        return Map.of("respuesta", respuestaBot);
    }
}
