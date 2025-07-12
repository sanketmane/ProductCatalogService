package com.example.productcatalogservice.services;

import com.example.productcatalogservice.clients.FakeStoreApiClient;
import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Practice the code here as it will be mostly asked during the mock interview

@Service
public class FakeProductService implements IProductService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private FakeStoreApiClient fakeStoreApiClient;

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

// Code before moving to FakeStoreClientApi
//    @Override
//    public Product getProductById(Long productId) {
//        RestTemplate restTemplate = restTemplateBuilder.build();
//        /* This is just to demonstrate use of getForObject() which doesn't support HTTP status codes
//        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject(
//                "https://fakestoreapi.com/products/{productId}",
//                FakeStoreProductDto.class,
//                productId
//        ); */
//        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = restTemplate.getForEntity(
//                "https://fakestoreapi.com/products/{productId}",
//                FakeStoreProductDto.class,
//                productId
//        );
//        // check if we get 200 status code and body in response from fakestoreproduct api
//        // else return null
//        if(fakeStoreProductDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)) &&
//                fakeStoreProductDtoResponseEntity.hasBody()) {
//            return from(fakeStoreProductDtoResponseEntity.getBody());
//        }
//        return null;
//
//    }

    @Override
    public Product getProductById(Long productId) {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreApiClient.getProductById(productId);
        if(fakeStoreProductDto == null){
            return null;
        }
        Product product = from(fakeStoreProductDto);
        return product;
    }


// Code before moving to FakeStoreClientApi
//    @Override
//    public Product createProduct(Product product) {
//        RestTemplate restTemplate = restTemplateBuilder.build();
//        FakeStoreProductDto fakeStoreProductDto = from(product);
//        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = restTemplate.postForEntity(
//                "https://fakestoreapi.com/products",
//                fakeStoreProductDto,
//                FakeStoreProductDto.class
//        );
//        if(fakeStoreProductDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)) &&
//        fakeStoreProductDtoResponseEntity.hasBody()) {
//            return from(fakeStoreProductDtoResponseEntity.getBody());
//        }
//        return null;
//
//    }

    public Product createProduct(Product product) {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreApiClient.createProduct(from(product));
        if(fakeStoreProductDto == null){
            return null;
        }
        Product createdProduct = from(fakeStoreProductDto);
        return createdProduct;
    }


// Code before moving to FakeStoreClientApi
//    @Override
//    public Product replaceProduct(Long productId, Product product) {
//        RestTemplate restTemplate = restTemplateBuilder.build();
//        FakeStoreProductDto fakeStoreProductDto = from(product);
//        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = requestForEntity(
//                HttpMethod.PUT,
//                "https://fakestoreapi.com/products/{productId}",
//                fakeStoreProductDto,
//                FakeStoreProductDto.class,
//                productId
//        );
//        if(fakeStoreProductDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)) &&
//        fakeStoreProductDtoResponseEntity.hasBody()) {
//            return from(fakeStoreProductDtoResponseEntity.getBody());
//        }
//        return null;
//
//    }
    public Product replaceProduct(Long productId, Product product) {
        FakeStoreProductDto inputFakeStoreProductDto = from(product);
        FakeStoreProductDto fakeStoreProductDtoOut = fakeStoreApiClient.replaceProduct(productId, inputFakeStoreProductDto);
        if (fakeStoreProductDtoOut == null) {
            return null;
        }
        return from(fakeStoreProductDtoOut);
    }

    // HW implementation
    // For delete, we will return the deleted entity in the body with 200 OK response.
    // Code before moving to FakeStoreClientApi
//    @Override
//    public Product deleteProduct(Long productId) {
//        RestTemplate restTemplate = restTemplateBuilder.build();
//        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = requestForEntity(
//                HttpMethod.DELETE,
//                "https://fakestoreapi.com/products/{productId}",
//                null,
//                FakeStoreProductDto.class,
//                productId
//        );
//        if(fakeStoreProductDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)) &&
//        fakeStoreProductDtoResponseEntity.hasBody()) {
//            return from(fakeStoreProductDtoResponseEntity.getBody());
//        }
//        return null;
//    }

    public Product deleteProduct(Long productId) {
        FakeStoreProductDto fakeStoreProductDtoOut = fakeStoreApiClient.deleteProduct(productId);
        if (fakeStoreProductDtoOut == null) {
            return null;
        }
        return from(fakeStoreProductDtoOut);
    }

    // We created
    public <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod, String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    // mapping method to convert FakeStoreProductDto object to Product
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

    // mapping method to convert Product object to FakeStoreProductDto
    private FakeStoreProductDto from(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getImageUrl());
        if(product.getCategory() != null) {
            fakeStoreProductDto.setCategory(product.getCategory().getName());
        }
        return fakeStoreProductDto;
    }
}
