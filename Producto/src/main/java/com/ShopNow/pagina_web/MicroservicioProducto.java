package com.ShopNow.pagina_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ShopNow.pagina_web.producto.model.User;
import com.ShopNow.pagina_web.producto.model.Role;
import com.ShopNow.pagina_web.producto.repository.UserRepository;

// PRODUCTO

@SpringBootApplication
@EnableFeignClients
public class MicroservicioProducto {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioProducto.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByUsername("admin").isEmpty()) {
                User user = new User();
                user.setUsername("admin");
                user.setPassword(encoder.encode("admin1234"));
                user.setRole(Role.ROLE_ADMIN);
                repo.save(user);
            }
        };
    }
}
