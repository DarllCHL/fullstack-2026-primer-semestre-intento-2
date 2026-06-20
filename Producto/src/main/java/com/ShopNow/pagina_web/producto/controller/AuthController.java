package com.ShopNow.pagina_web.producto.controller;

import com.ShopNow.pagina_web.producto.dto.AuthResponse;
import com.ShopNow.pagina_web.producto.dto.LoginRequest;
import com.ShopNow.pagina_web.producto.model.Role;
import com.ShopNow.pagina_web.producto.model.User;
import com.ShopNow.pagina_web.producto.security.JwtService;
import com.ShopNow.pagina_web.producto.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "🔓 Autenticación", description = "Endpoints públicos de acceso al sistema. El token JWT retornado contiene el rol del usuario: ROLE_ADMIN tiene acceso total, ROLE_USER solo accede a endpoints de Cliente.")
public class AuthController {

    @Autowired private UserService userService;
    @Autowired private JwtService jwtService;
    @Autowired private PasswordEncoder passwordEncoder;

    @Operation(
            summary = "Iniciar sesión",
            description = "Autentica al usuario y retorna un token JWT. Válido por 1 hora. Usarlo como Bearer Token en los endpoints protegidos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login exitoso, retorna token JWT"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            User user = userService.findByUsername(request.getUsername());
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
            }
            String token = jwtService.generateToken(user.getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    @Operation(
            summary = "Registrar usuario del sistema",
            description = "Crea un nuevo usuario con rol ADMIN. Solo puede ser usado por un ADMIN autenticado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error al crear el usuario, puede que el username ya exista")
    })
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginRequest request) {
        try {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setRole(Role.ROLE_ADMIN);
            userService.save(user);
            return ResponseEntity.ok("Usuario creado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear usuario: " + e.getMessage());
        }
    }
}