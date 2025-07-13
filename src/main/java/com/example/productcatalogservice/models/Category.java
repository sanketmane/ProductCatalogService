package com.example.productcatalogservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel {
    private String name;
    private String description;

    // Since category field is already mapped with M:1 cardinality,
    // we don't want to do it again here, hence mappedBy is used below.
    // This tells JPA, not to do the mapping again and just ignore this relationship.
    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public Category() {
        this.setCreatedAt(new Date());
        this.setLastUpdatedAt(new Date());
        this.setState(State.ACTIVE);
        this.products = new ArrayList<>();
    }
}
