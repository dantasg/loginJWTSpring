package com.dtec.crudyoutube.controllers;

import com.dtec.crudyoutube.domain.product.Product;
import com.dtec.crudyoutube.domain.product.RequestProduct;
import com.dtec.crudyoutube.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity getAllProducts() {
        var allProducts = productRepository.findAll();
        return ResponseEntity.ok(allProducts);
    }

    @PostMapping
    public ResponseEntity registerProduct(@RequestBody @Valid RequestProduct data) {
        Product product = new Product(data);
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity updateProduct(@RequestBody @Valid RequestProduct data) {
        Optional<Product> product = productRepository.findById(data.id());

        //        product.setName(data.name());
//        product.setPrice_in_cents(data.price_in_cents());

        return ResponseEntity.ok(product);
    }
}
