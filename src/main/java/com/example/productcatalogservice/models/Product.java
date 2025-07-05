package com.example.productcatalogservice.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Product extends BaseModel {
    private String name;
    private String description;
    private Double price;
    private String imageUrl;

    public Product() {
        // below setter methods are available because of lombok
        // Getter and Setter annotations in the BaseModel
        this.setCreatedAt(new Date());
        this.setLastUpdatedAt(new Date());
        this.setState(State.ACTIVE);
    }
}
