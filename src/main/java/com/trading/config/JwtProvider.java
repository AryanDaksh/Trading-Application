package com.trading.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.trading.utils.AppConstants.*;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 08-08-2025 21:54
 */

public class JwtProvider {
    private static SecretKey secretKey = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());

    public static String generateToken(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles = popuplateAuthorities(authorities);
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000))
                .claim(EMAIL, authentication.getName())
                .claim(AUTHORITIES, roles)
                .signWith(secretKey)
                .compact();
    }

    public static String getEmailFromToken(String token) {
        token = token.substring(7);
        Claims claims = Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return String.valueOf(claims.get(EMAIL));
    }

    private static String popuplateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auth = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            auth.add(authority.getAuthority());
        }
        return String.join(",", auth);
    }
}
