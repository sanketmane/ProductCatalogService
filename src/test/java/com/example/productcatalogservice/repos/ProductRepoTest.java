package com.example.productcatalogservice.repos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductRepoTest {

    @Autowired
    private ProductRepo productRepo;

    @Test
    public void testQueries() {
//      List<Product> productList = productRepo.findAllByOrderByPriceDesc();
//      for(Product product : productList) {
//          System.out.println(product.getPrice());
//      }
        System.out.println(productRepo.getMeDescriptionForProductId(1L)); //should return asus laptop
    }

}