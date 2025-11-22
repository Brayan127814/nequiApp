package com.nequiApp.nequiApp.utils;

import com.nequiApp.nequiApp.Entities.UsuarioEntity;
import com.nequiApp.nequiApp.Repositorys.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.flogger.Flogger;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final UsuarioRepository usuarioRepository;
    private final JwtUtils jwtUtils;
    public static final String[] PUBLIC_URLS = {
            "/api/usuarios/register",
            "/api/usuarios/login"
    };

    public JwtAuthFilter(UsuarioRepository usuarioRepository, JwtUtils jwtUtils) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtils = jwtUtils;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String requestUri = request.getRequestURI();

        boolean isPublic = Arrays.stream(PUBLIC_URLS).anyMatch(requestUri::equals);


        if (isPublic) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null && !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        //Validar token
        if (!jwtUtils.validarToken(token)) {
            logger.error("Error token invalido ");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("No se puede inicioar sesion token invalido");

        }

        try {

            String email = jwtUtils.getEmailFromToken(token);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsuarioEntity usuario = usuarioRepository.findByEmail(email);
                if (usuario != null) {
                    var authorities = usuario.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).toList();
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuario.getEmail(), null, authorities);

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e) {
            logger.error("Error" + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("ERROR DE FILTRO");
        }

        filterChain.doFilter(request, response);
    }
}

