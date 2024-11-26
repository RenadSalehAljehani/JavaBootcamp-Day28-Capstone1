package com.example.ecommercewebsite.Controller;

import com.example.ecommercewebsite.ApiResponse.ApiResponse;
import com.example.ecommercewebsite.Model.Category;
import com.example.ecommercewebsite.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    // 1. Declares a final dependency for CategoryService, used to perform operations related to categories
    private final CategoryService categoryService;

    // 2. CRUD endpoints
    // 2.1 Create(post)
    @PostMapping("/addCategory")
    public ResponseEntity addCategory(@RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        categoryService.addCategory(category);
        return ResponseEntity.status(200).body(new ApiResponse("New Category Added."));
    }

    // 2.2 Read(get)
    @GetMapping("/getCategories")
    public ResponseEntity getCategories(){
        return ResponseEntity.status(200).body(categoryService.getCategories());
    }

    // 2.3 Update(put)
    @PutMapping("/updateCategory/{id}")
    public ResponseEntity updateCategory(@PathVariable String id, @RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(categoryService.updateCategory(id, category)){
            return ResponseEntity.status(200).body(new ApiResponse("Category Updated."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Category Id Not Found."));
    }

    // 2.4 Delete
    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity deleteCategory(@PathVariable String id){
        if(categoryService.deleteCategory(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Category deleted."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Category Id Not Found."));
    }
}