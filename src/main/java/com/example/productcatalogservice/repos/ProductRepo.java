package com.example.productcatalogservice.repos;

import com.example.productcatalogservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // This annotation tells Spring: This class is intended to access the database (or any persistence store).
// Explanation of types in JpaRepository<Product, Long>
// Product - This means the table to which Product class is mapped to
// Long - DataType of the primary key for Product table, which is id and is of type Long.
public interface ProductRepo extends JpaRepository<Product, Long> {
}
