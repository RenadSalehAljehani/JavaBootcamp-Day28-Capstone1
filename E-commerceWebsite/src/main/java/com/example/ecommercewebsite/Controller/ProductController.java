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

    // 2. Declares a final dependency for CategoryService, used to perform operations related to categories
    private final CategoryService categoryService;

    // 3. Declares a final dependency for UserService, used to perform operations related to users
    private final UserService userService;

    // 4. CRUD endpoints
    // 4.1 Create(post)
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

    // 4.2 Read(get)
    @GetMapping("/getProducts")
    public ResponseEntity getProducts(){
        return ResponseEntity.status(200).body(productService.getProducts());
    }

    // 4.3 Update(put)
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

    // 4.4 Delete
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id){
        if(productService.deleteProduct(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Product Deleted."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Product Id Not Found."));
    }

    // ** New endpoint **
    // 5. Extra 5 endpoints
    // 5.1 get products within user budget
    @GetMapping("/getProductsWithinBudget/{userId}")
    public ResponseEntity getProductsWithinBudget(@PathVariable String userId) {
        // Call the service method to get products within user budget
        ArrayList<Product> productsWithinBudget = productService.getProductsWithinBudget(userId);

        // Check for null scenarios and handle them specifically
        if (productsWithinBudget == null) {
            // Check if the user exists
            if (!userService.isUserExists(userId)) {
                return ResponseEntity.status(400).body(new ApiResponse("User Not Found."));
            } else {
                return ResponseEntity.status(400).body(new ApiResponse("No Suggested Products Within the User's Budget."));
            }
        }
        return ResponseEntity.status(200).body(productsWithinBudget);
    }
    
    // 5.2 Gift finder
    @GetMapping("/giftFinder/{userId}/{categoryId}/{recipientAge}")
    public ResponseEntity giftFinder(@PathVariable String userId, @PathVariable String categoryId, @PathVariable int recipientAge) {
        // Call the service method to get the suggested gifts
        ArrayList<Product> suggestedGifts = productService.giftFinder(userId, categoryId, recipientAge);

        // Check for null scenarios and handle them specifically
        if (suggestedGifts == null) {
            if (productService.getProductsWithinBudget(userId) == null) {
                if (!userService.isUserExists(userId)) {// Check if the user exists
                    return ResponseEntity.status(400).body(new ApiResponse("User Not Found."));
                } else { // If no products within user budget
                    return ResponseEntity.status(400).body(new ApiResponse("No Suggested Products Within the User's Budget."));
                }
            }
            if (!categoryService.isCategoryExists(categoryId)) { // Check if the user exists
                return ResponseEntity.status(400).body(new ApiResponse("Category Not Found."));
            } else {  // If no products match the category or age filter
                return ResponseEntity.status(400).body(new ApiResponse("No Suggested Gifts Found for the Specified Category and Recipient Age."));
            }
        }
        // Return the list of suggested gifts if found
        return ResponseEntity.status(200).body(suggestedGifts);
    }
}
