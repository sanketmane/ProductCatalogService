package com.example.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDto {
    // attributes are determined based on the actual FakeStoreApi fields
    // https://fakestoreapi.com/products/1
    // we will exclude the ratings part here as it is not imp from our perspective
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
}
