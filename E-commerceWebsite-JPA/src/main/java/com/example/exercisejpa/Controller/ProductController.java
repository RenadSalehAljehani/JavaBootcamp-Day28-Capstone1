package com.example.exercisejpa.Controller;

import com.example.exercisejpa.ApiResponse.ApiResponse;
import com.example.exercisejpa.Model.Product;
import com.example.exercisejpa.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    // 1. Declare a dependency for ProductService using Dependency Injection
    private final ProductService productService;

    // 2. CRUD
    // 2.1 Get
    @GetMapping("/get")
    public ResponseEntity getAllProducts() {
        return ResponseEntity.status(200).body(productService.getAllProducts());
    }

    // 2.2 Post
    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (productService.addProduct(product)) {
            return ResponseEntity.status(200).body(new ApiResponse("New Product Added."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Category Not Found."));
    }

    // 2.3 Update
    @PutMapping("/update/{id}")
    public ResponseEntity updateProduct(@PathVariable Integer id, @RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        return ResponseEntity.status(200).body(new ApiResponse(productService.updateProduct(id, product)));
    }

    // 2.4 Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable Integer id) {
        if (productService.deleteProduct(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("Product Deleted."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Product Not Found."));
    }
}