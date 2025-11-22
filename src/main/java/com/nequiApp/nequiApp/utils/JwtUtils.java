package com.nequiApp.nequiApp.utils;

import com.nequiApp.nequiApp.Entities.UsuarioEntity;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.coyote.http11.filters.SavedRequestInputFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.security.config.Customizer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;
import java.util.SequencedSet;
import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    @Value("${jwt.secret:miClaveSecretaPorDefecto12345678901234567890}")
    private String secretString;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
    }

    private static final long EXPIRATION_TIME = 86400000;

    public String generarToken(UsuarioEntity usuario) {
        List<String> roles = usuario.getRoles().stream()
                .map(u -> u.getRoleName())
                .toList();

        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmailFromToken(String token) {
        try {
            if (token == null || token.isEmpty() || token.equals("null")) {
                return null;
            }

            return Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

        } catch (Exception e) {
            return null;
        }
    }

    public boolean validarToken(String token) {
        try {
            if (token == null || token.isEmpty()) return false;

            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}


/**
 *
 *
 * //Generar token
 * public static String generarToken(String email) {
 * return Jwts.builder().setSubject(email).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).signWith(SECRET_KEY).compact();
 * }
 * <p>
 * //extraer el email
 * public static String getEmailFromToken(String token) {
 * return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody().getSubject();
 * }
 * <p>
 * //Validar token
 * public static boolean validarToken(String token) {
 * try {
 * Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJwt(token);
 * return true;
 * } catch (Exception e) {
 * return false;
 * }
 */



