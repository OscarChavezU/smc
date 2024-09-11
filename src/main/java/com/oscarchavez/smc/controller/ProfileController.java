package com.oscarchavez.smc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oscarchavez.smc.jwt.JwtService;
import com.oscarchavez.smc.model.User;
import com.oscarchavez.smc.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @PostMapping("/userinfo")
    public ResponseEntity<Map<String, Object>> getUserInfo(@RequestHeader("Authorization") String token) {
        // Extraer el token sin la palabra "Bearer "
        token = token.substring(7);
        
        String username = jwtService.getUsernameFromToken(token);
        
        // Buscar el usuario en la base de datos usando el repositorio
        User user = userRepository.findByUsuario(username).orElseThrow();

        // Crear el response con la información requerida
        Map<String, Object> response = new HashMap<>();
        response.put("usuario", user.getUsername());
        response.put("areaacceso", user.getAreaacceso());
        response.put("idusuario", user.getId());
        response.put("idsucursal", user.getIdsucursal());

        return ResponseEntity.ok(response);
    }
}
