package com.example.exercisejpa.Controller;

import com.example.exercisejpa.ApiResponse.ApiResponse;
import com.example.exercisejpa.Model.Category;
import com.example.exercisejpa.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    // 1. Declare a dependency for CategoryService using Dependency Injection
    private final CategoryService categoryService;

    // 2. CRUD
    // 2.1 Get
    @GetMapping("/get")
    public ResponseEntity getAllCategories(){
        return ResponseEntity.status(200).body(categoryService.getAllCategories());
    }

    // 2.2 Post
    @PostMapping("/add")
    public ResponseEntity addCategory(@RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        categoryService.addCategory(category);
        return ResponseEntity.status(200).body(new ApiResponse("New Category Added."));
    }

    // 2.3 Update
    @PutMapping("/update/{id}")
    public ResponseEntity updateCategory(@PathVariable Integer id, @RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(categoryService.updateCategory(id,category)){
            return ResponseEntity.status(200).body(new ApiResponse("Category Updated."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Category Not Found."));
    }

    // 3.4 Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable Integer id){
        if(categoryService.deleteCategory(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Category Deleted."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Category Not Found."));
    }
}