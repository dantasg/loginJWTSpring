package com.dtec.crudyoutube.repositories;

import com.dtec.crudyoutube.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
