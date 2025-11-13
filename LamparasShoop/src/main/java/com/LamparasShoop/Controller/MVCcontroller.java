package com.LamparasShoop.Controller;

import com.LamparasShoop.Model.Usuario;
import com.LamparasShoop.Repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MVCcontroller {

    private final UsuarioRepository usuarioRepository;

    @GetMapping("/index")
    public String index(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String username = auth.getName();
            Usuario usuario = usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
            model.addAttribute("usuario", usuario);
        } else {
            model.addAttribute("usuario", null);
        }
        return "index";
    }

    @GetMapping("/carrito")
    public String carrito(){
        return "carrito";
    }

    @GetMapping("/lista")
    public String lista(){ return "lista";}

}
