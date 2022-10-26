package org.henry.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.henry.configuration.Config;
import org.henry.model.Product;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProductService {

    public static JsonMapper mapper = new JsonMapper();

    @Inject
    Config config;

    public void save(Product product) throws JsonProcessingException {
        config.productDao().save(new Product(UUID.randomUUID(), product.getDescription()));
    }

    public void update(Product product, UUID productId) {
        Optional<Product> res = Optional.ofNullable(config.productDao().findById(productId));
        if (res.isPresent()) {
            res.get().setDescription(product.getDescription());
            config.productDao().update(res.get());
        }
    }

    public Product findById(UUID productId) {
        return config.productDao().findById(productId);
    }

    public List<Product> getAll() {
        return config.productDao().findAll().all();
    }

    public void delete(Product product) {
        config.productDao().delete(product);
    }

}