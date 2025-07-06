package com.example.productcatalogservice.services;

import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FakeProductService implements IProductService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    // HW implementation of getAllProductDetails
    // Get FakeStoreProductDto[] as input and generate List<Product> as output
    // which is then given to ProductController
    @Override
    public List<Product> getAllProductDetails() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> fakeStoreProductDtoResponseEntity = restTemplate.getForEntity(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto[].class
        );
        List<Product> products = new ArrayList<>();
        if(fakeStoreProductDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)) &&
                fakeStoreProductDtoResponseEntity.hasBody()){
            FakeStoreProductDto[] fakeStoreProductDtoResponseEntityBody = fakeStoreProductDtoResponseEntity.getBody();
            for (FakeStoreProductDto dto : fakeStoreProductDtoResponseEntityBody) {
                Product product = from(dto);
                products.add(product);
            }
        }
        return products;
    }

    @Override
    public Product getProductById(Long productId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        /* This is just to demonstrate use of getForObject() which doesn't support HTTP status codes
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/{productId}",
                FakeStoreProductDto.class,
                productId
        ); */
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/{productId}",
                FakeStoreProductDto.class,
                productId
        );
        // check if we get 200 status code and body in response from fakestoreproduct api
        // else return null
        if(fakeStoreProductDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)) &&
                fakeStoreProductDtoResponseEntity.hasBody()) {
            return from(fakeStoreProductDtoResponseEntity.getBody());
        }
        return null;

    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    // method to convert FakeStoreProductDto object to Product
    private Product from(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }
}
