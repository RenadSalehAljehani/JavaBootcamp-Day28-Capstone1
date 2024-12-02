package com.example.exercisejpa.Controller;

import com.example.exercisejpa.ApiResponse.ApiResponse;
import com.example.exercisejpa.Model.MerchantStock;
import com.example.exercisejpa.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchantStock")
@RequiredArgsConstructor
public class MerchantStockController {

    // 1. Declare a dependency for MerchantStockService using Dependency Injection
    private final MerchantStockService merchantStockService;

    // 2. CRUD
    // 2.1 Get
    @GetMapping("/get")
    public ResponseEntity getAllMerchantStocks() {
        return ResponseEntity.status(200).body(merchantStockService.getAllMerchantStocks());
    }

    // 2.2 Post
    @PostMapping("/add")
    public ResponseEntity addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        merchantStockService.addMerchantStock(merchantStock);
        return ResponseEntity.status(200).body(new ApiResponse("New Merchant Stock Added."));
    }

    // 2.3 Update
    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchantStock(@PathVariable Integer id, @RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        return ResponseEntity.status(200).body(new ApiResponse(merchantStockService.updateMerchantStock(id, merchantStock)));
    }

    // 2.4 Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchantStock(@PathVariable Integer id) {
        if (merchantStockService.deleteMerchantStock(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Stock Deleted."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant Stock Not Found."));
    }
}