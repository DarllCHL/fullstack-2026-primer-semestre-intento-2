package com.ShopNow.pagina_web.producto;

import com.ShopNow.pagina_web.producto.model.Producto;
import com.ShopNow.pagina_web.producto.repository.ProductoRepositoryJpa;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ProductoRepositoryJpa productoRepository;

    @Override
    public void run(String... args) throws Exception {

        if (productoRepository.count() > 0) {
            System.out.println(">>> DataLoader: BD ya tiene productos, se omite la carga.");
            return;
        }

        Faker faker = new Faker();

        for (int i = 0; i < 20; i++) {
            Producto producto = new Producto();
            producto.setNombre(faker.commerce().productName());
            producto.setDescripcion(faker.lorem().sentence(10));
            producto.setPrecio(Double.parseDouble(faker.commerce().price(10.0, 9999.0)));
            producto.setStock(faker.number().numberBetween(1, 200));
            producto.setActivo(true);
            productoRepository.save(producto);
        }

        System.out.println(">>> DataLoader: 20 productos de prueba generados exitosamente.");
    }
}