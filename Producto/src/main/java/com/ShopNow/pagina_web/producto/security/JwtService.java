package com.ShopNow.pagina_web.producto.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtException;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final Key SECRET_KEY;

    public JwtService(@Value("${jwt.secret}") String secret) {
        this.SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();

            return username.equals(userDetails.getUsername())
                    && expiration.after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}














// package com.ShopNow.pagina_web.usuario.security;

// import org.springframework.stereotype.Service;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.security.Keys;
// import io.jsonwebtoken.JwtException;

// import org.springframework.security.core.userdetails.UserDetails;

// import java.security.Key;
// import java.util.Date;

// @Service
// public class JwtService {

//     private final Key SECRET_KEY = Keys.hmacShaKeyFor(
//         "secretKeysecretKeysecretKeysecretKey".getBytes()
//     );

//     public String generateToken(String username) {
//         return Jwts.builder()
//                 .setSubject(username)
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + 86400000))
//                 .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
//                 .compact();
//     }

//     public String extractUsername(String token) {
//         return Jwts.parserBuilder()
//                 .setSigningKey(SECRET_KEY)
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody()
//                 .getSubject();
//     }

//     // ✅ NUEVO: valida que el token pertenezca al usuario y no esté expirado
//     public boolean isTokenValid(String token, UserDetails userDetails) {
//         try {
//             String username = extractUsername(token);
//             Date expiration = Jwts.parserBuilder()
//                     .setSigningKey(SECRET_KEY)
//                     .build()
//                     .parseClaimsJws(token)
//                     .getBody()
//                     .getExpiration();

//             return username.equals(userDetails.getUsername())
//                     && expiration.after(new Date());
//         } catch (JwtException | IllegalArgumentException e) {
//             return false; // token inválido o expirado
//         }
//     }
// }
























// package com.ShopNow.pagina_web.security;

// import org.springframework.stereotype.Service;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.security.Keys;

// import java.security.Key;
// import java.util.Date;

// @Service
// public class JwtService {

//     // Genera una clave segura a partir de tu secreto
//     private final Key SECRET_KEY = Keys.hmacShaKeyFor("secretKeysecretKeysecretKeysecretKey".getBytes());

//     public String generateToken(String username) {
//         return Jwts.builder()
//                 .setSubject(username)
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 horas
//                 .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
//                 .compact();
//     }

//     public String extractUsername(String token) {
//         return Jwts.parserBuilder()
//                 .setSigningKey(SECRET_KEY)
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody()
//                 .getSubject();
//     }
// }