package org.henry.repository;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;
import org.henry.model.Product;

import java.util.UUID;

@Dao
public interface ProductDao {
    @Select
    Product findById(UUID productId);

    @Select
    PagingIterable<Product> findAll();

    @Update
    void update(Product product);

    @Insert
    void save(Product product);

    @Delete
    void delete(Product product);
}