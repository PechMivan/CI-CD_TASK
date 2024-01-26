package com.product.springbootapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductTests {

    @Autowired
    private ProductRepository repo;

    @Test
    @Rollback(value = false)
    public void testCreateProduct(){
        Product product = new Product("Teddy bear", 150);
        Product savedProduct = repo.save(product);

        Assertions.assertNotNull(savedProduct);
    }

    @Test
    public void testFindProductByNameExist(){
        String name = "Teddy bear";
        Product product = repo.findByName(name);

        Assertions.assertEquals(product.getName(), name);
    }

    @Test
    public void testFindProductByNameNotExist(){
        String name = "Teddy";
        Product product = repo.findByName(name);

        Assertions.assertNull(product);
    }

    @Test
    @Rollback(value = false)
    public void testUpdateProduct(){
        String productName = "Gummy Bear";
        Product product = new Product(productName, 200);
        product.setId(2);

        repo.save(product);

        Product updatedProduct = repo.findByName(productName);

        Assertions.assertEquals(updatedProduct.getName(), productName);
    }

    @Test
    public void testListProducts(){
        List<Product> products = (List<Product>) repo.findAll();

        for (Product product : products){
            System.out.println(product);
        }

        Assertions.assertEquals(products.size(), 0);
    }

    @Test
    @Rollback(value = false)
    public void testDeleteProduct(){
        Integer id = 2;

        boolean isExistantBeforeDelete = repo.findById(id).isPresent();
        repo.deleteById(id);
        boolean isExistantAfterDelete = repo.findById(id).isPresent();

        Assertions.assertTrue(isExistantBeforeDelete);
        Assertions.assertFalse(isExistantAfterDelete);
    }
}
