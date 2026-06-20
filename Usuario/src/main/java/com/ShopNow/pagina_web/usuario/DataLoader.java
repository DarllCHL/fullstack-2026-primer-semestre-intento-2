package com.ShopNow.pagina_web.usuario;

import com.ShopNow.pagina_web.usuario.model.Usuario;
import com.ShopNow.pagina_web.usuario.repository.UsuarioRepositoryJpa;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepositoryJpa usuarioRepository;

    @Override
    public void run(String... args) throws Exception {

        if (usuarioRepository.count() > 0) {
            System.out.println(">>> DataLoader: BD ya tiene usuarios, se omite la carga.");
            return;
        }

        Faker faker = new Faker();

        for (int i = 0; i < 20; i++) {
            Usuario usuario = new Usuario();
            usuario.setNombre(faker.name().firstName());
            usuario.setApellido(faker.name().lastName());
            usuario.setEmail(faker.internet().emailAddress());
            usuario.setTelefono("+569" + faker.number().numberBetween(10000000, 99999999));
            usuario.setDireccion(faker.address().streetAddress() + ", Santiago");
            usuarioRepository.save(usuario);
        }

        System.out.println(">>> DataLoader: 20 usuarios de prueba generados exitosamente.");
    }
}