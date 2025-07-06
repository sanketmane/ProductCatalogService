package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.CategoryDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
// This annotation helps to indicate this class follows REST principles
// hence the output will be either JSON or XML.
public class ProductController {
    // ProductController is a Bean or singleton object which we will use
    // across the Product Catalog service.

    @Autowired // for automatically handling dependency injection
    private IProductService productService; //currently refers to only FakeProductService

    @GetMapping("/products")
//    public List<Product> getAllProducts() {
//        List<Product> products = new ArrayList<>();
//        Product product = new Product();
//        product.setId(1L);
//        product.setName("MacBook Pro");
//        products.add(product);
//        return products;
//    }
    // HW implementation of getAllProducts()
    // Above was basic implementation and below is actual one.
    // Takes List<Product> as input
    // Generates List<ProductDto> as output to be consumed by clients.
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<Product> allProductDetails = productService.getAllProductDetails();
        if(allProductDetails == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        List<ProductDto> productDtoList = new ArrayList<>();
        for(Product product : allProductDetails){
            ProductDto productDto = from(product); // translate product to productDto
            productDtoList.add(productDto);
        }
        // No need to define ResponseEntity<List<ProductDto> since method
        // return type already defines that, so just doing ResponseEntity<>
        // should suffice.
        return new ResponseEntity<>(productDtoList, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    // PathVariable maps API params to the actual method params
    // If API param name == Method param name, then just @PathVariable should suffice
    // otherwise declare API param name in ("") after @PathVariable annotation.
//    public Product getProductById(@PathVariable("id") Long productId) {
//        Product product = new Product();
//        product.setId(productId);
//        return product;
//    }
    public ResponseEntity<ProductDto>  getProductById(@PathVariable("id") Long productId){
        if(productId < 0){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Product product = productService.getProductById(productId);
        if(product == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        ProductDto productDto = from(product);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PostMapping("/products")
    // @RequestBody annotation maps post body to product object
    // Translation from JSON to object and vice versa is handled
    // by library called Jackson in Java.
    // Also, it's a good practice to just return the same created object
    // in response.
    public Product createProduct(@RequestBody Product product) {
        List<Product> products = productService.getAllProductDetails();
        products.add(product);
        return product;
    }

    @DeleteMapping("/products/{id}")
    public String deleteProduct(@PathVariable("id") Long productId) {
        List<Product> products = productService.getAllProductDetails();
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
        List<Product> products = productService.getAllProductDetails();
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

    // method to convert Product object to ProductDto
    private ProductDto from(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        if(product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(product.getCategory().getId()); // this will be null, because we don't have Id field in Category class
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setDescription(product.getCategory().getDescription());
            productDto.setCategory(categoryDto);
        }
        return productDto;
    }
}
