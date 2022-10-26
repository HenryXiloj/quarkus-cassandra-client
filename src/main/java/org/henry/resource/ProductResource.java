package org.henry.resource;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.henry.model.Product;
import org.henry.service.ProductService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService productService;

    @POST
    public void save(Product product) throws JsonProcessingException {
        productService.save(product);
    }

    @PUT
    @Path("{productId}")
    public void update(Product product, UUID productId) {
        productService.update(product,productId);
    }

    @GET
    @Path("{productId}")
    public Product findById(UUID productId) {
        return productService.findById(productId);
    }

    @GET
    public List<Product> getAll() {
        return productService.getAll();
    }

    @DELETE
    public void delete(Product product) {
        productService.delete(product);
    }


}
