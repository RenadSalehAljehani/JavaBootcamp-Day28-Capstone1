package com.example.ecommercewebsite.Controller;

import com.example.ecommercewebsite.ApiResponse.ApiResponse;
import com.example.ecommercewebsite.Model.Product;
import com.example.ecommercewebsite.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    // 1. Declares a final dependency for ProductService, used to perform operations related to products
    private final ProductService productService;

    // 2. CRUD endpoints
    // 2.1 Create(post)
    @PostMapping("/addProduct")
    public ResponseEntity addProduct(@RequestBody @Valid Product product, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(productService.addProduct(product)){
            return ResponseEntity.status(200).body(new ApiResponse("New Product Added."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Category Id Not Found."));
    }

    // 2.2 Read(get)
    @GetMapping("/getProducts")
    public ResponseEntity getProducts(){
        return ResponseEntity.status(200).body(productService.getProducts());
    }

    // 2.3 Update(put)
    @PutMapping("/updateProduct/{id}")
    public ResponseEntity updateProduct(@PathVariable String id, @RequestBody @Valid Product product, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(productService.updateProduct(id,product)){
            return ResponseEntity.status(200).body(new ApiResponse("Product Updated."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Product Id Not Found."));
    }

    // 2.4 Delete
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id){
        if(productService.deleteProduct(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Product Deleted."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Product Id Not Found."));
    }

    // 3. Extra 5 endpoints
    // 3.1 Gift finder
    @GetMapping("/giftFinder/{minAge}/{maxAge}/{category}")
    public ResponseEntity giftFinder(@PathVariable int minAge, @PathVariable int maxAge, @PathVariable String category){
        if(productService.giftFinder(minAge,maxAge,category) != null){
            return ResponseEntity.status(200).body(productService.giftFinder(minAge,maxAge,category));
        }
        return ResponseEntity.status(400).body(new ApiResponse("No Suggested Gifts Found."));
    }
}