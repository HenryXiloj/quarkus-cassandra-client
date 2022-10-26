package org.henry.configuration;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.henry.mapper.InventoryMapper;
import org.henry.mapper.InventoryMapperBuilder;
import org.henry.repository.ProductDao;

import javax.inject.Singleton;

@Singleton
public class Config {

    /**
     * Call here multiple keyspace with respective DAO
     * */
    @ConfigProperty(name = "keyspace.one")
    String keyspace1;

    public CqlSession session(){
        CqlSession session = CqlSession.builder().build();
        return session;
    }

    public ProductDao productDao(){
        System.out.println("keyspace "+ keyspace1);
        InventoryMapper inventoryMapper = new InventoryMapperBuilder(session()).build();
        ProductDao dao = inventoryMapper.productDao(CqlIdentifier.fromCql(keyspace1));
        return dao;
    }
}
