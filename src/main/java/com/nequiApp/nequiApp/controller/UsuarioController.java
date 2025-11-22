package com.nequiApp.nequiApp.controller;


import com.nequiApp.nequiApp.Entities.UsuarioEntity;
import com.nequiApp.nequiApp.Repositorys.UsuarioRepository;
import com.nequiApp.nequiApp.Services.UsuariosService;
import com.nequiApp.nequiApp.dtos.LoginRequest;
import com.nequiApp.nequiApp.dtos.UsuarioRequest;
import com.nequiApp.nequiApp.dtos.UsuarioResponse;
import com.nequiApp.nequiApp.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {


    public final UsuarioRepository usuarioRepository;
    private final UsuariosService usuariosService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public UsuarioController(UsuariosService usuariosService, AuthenticationManager authenticationManager, UsuarioRepository usuarioRepository, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.usuariosService = usuariosService;
        this.usuarioRepository = usuarioRepository;
        this.jwtUtils = jwtUtils;

    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> add(@RequestBody UsuarioRequest data) {
        UsuarioResponse usuario = usuariosService.register(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest data) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword())
            );

            String email = authentication.getName();

            UsuarioEntity usuario = usuarioRepository.findByEmail(email);
            String token = jwtUtils.generarToken(usuario);

            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales invalidas");
        }

    }

}


/**
 * //Otener usuarios
 *
 * @GetMapping("/all") public ResponseEntity<List<UsuarioResponse>> listar() {
 * List<UsuarioResponse> usuarios = usuariosService.listartodos();
 * return ResponseEntity.ok(usuarios);
 * }
 * @GetMapping("/usuario/{id}") public ResponseEntity<UsuarioResponse> usuarioById(@PathVariable Long id) {
 * UsuarioResponse usuario = usuariosService.getUserById(id);
 * return ResponseEntity.ok(usuario);
 * }
 * @PostMapping("/login") public ResponseEntity<?> login(@RequestBody LoginRequest data) {
 * try {
 * Authentication authentication = authenticationManager.authenticate(
 * new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword())
 * );
 * <p>
 * String email = authentication.getName();
 * <p>
 * UsuarioEntity usuario = usuarioRepository.findByEmail(email);
 * String token = jwtUtils.generarToken(usuario);
 * <p>
 * return ResponseEntity.ok(token);
 * } catch (Exception e) {
 * return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales invalidas");
 *
 *
 * ***********************************
 *
 @PostMapping("/register")
 public ResponseEntity<UsuarioResponse> add(@RequestBody UsuarioRequest data) {
 UsuarioResponse usuario = usuariosService.register(data);
 return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
 }

 @PostMapping("/login")
 public ResponseEntity<?> login(@RequestBody LoginRequest data) {
 try {
 Authentication authentication = authenticationManager.authenticate(
 new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword())
 );

 String email = authentication.getName();

 UsuarioEntity usuario = usuarioRepository.findByEmail(email);
 String token = jwtUtils.generarToken(usuario);

 return ResponseEntity.ok(token);
 } catch (Exception e) {
 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales invalidas");
 }

 }

  * }
 */