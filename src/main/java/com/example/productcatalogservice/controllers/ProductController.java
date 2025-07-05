package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.models.Product;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController // This annotation helps to indicate this follows REST
public class ProductController {
    // ProductController is a Bean or singleton object which we will use
    // across the Product Catalog service.
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setId(1L);
        product.setName("MacBook Pro");
        products.add(product);
        return products;
    }

    @GetMapping("/products/{id}")
    // PathVariable maps API params to the actual method params
    // If API param name == Method param name, then just @PathVariable should suffice
    // otherwise declare API param name in ("") after @PathVariable annotation.
    public Product getProductById(@PathVariable("id") Long productId) {
        Product product = new Product();
        product.setId(productId);
        return product;
    }

    @PostMapping("/products")
    // @RequestBody annotation maps post body to product object
    // Translation from JSON to object and vice versa is handled
    // by library called Jackson in Java.
    public Product createProduct(@RequestBody Product product) {
        List<Product> products = getAllProducts();
        products.add(product);
        return product;
    }

    @DeleteMapping("/products/{id}")
    public String deleteProduct(@PathVariable("id") Long productId) {
        List<Product> products = getAllProducts();
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                products.remove(product);
                return "Product deleted!";
            }
        }
        return "Product not found!";
    }


    // if product with the given id exists, we return the updated product
    // otherwise return null response
    @PutMapping("/products/{id}") // PUT request always needs a body
    public Product updateProduct(@PathVariable("id") Long productId, @RequestBody Product product) {
        List<Product> products = getAllProducts();
        for (Product prod : products) {
            if (prod.getId().equals(productId)) {
                prod.setName(product.getName());
                prod.setDescription(product.getDescription());
                prod.setPrice(product.getPrice());
                return prod;
            }
        }
        return null;
    }
}
