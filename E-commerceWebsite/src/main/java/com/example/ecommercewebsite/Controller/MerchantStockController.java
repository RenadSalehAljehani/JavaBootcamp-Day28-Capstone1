package com.example.ecommercewebsite.Controller;

import com.example.ecommercewebsite.ApiResponse.ApiResponse;
import com.example.ecommercewebsite.Model.MerchantStock;
import com.example.ecommercewebsite.Model.Product;
import com.example.ecommercewebsite.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchantStock")
@RequiredArgsConstructor
public class MerchantStockController {

    // 1. Declares a final dependency for MerchantStockService, used to perform operations related to merchantStocks
    private final MerchantStockService merchantStockService;

    // 2. CRUD endpoints
    // 2.1 Create(post)
    @PostMapping("/addMerchantStock")
    public ResponseEntity addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        return ResponseEntity.status(200).body(new ApiResponse(merchantStockService.addMerchantStock(merchantStock)));
    }

    // 2.2 Read(get)
    @GetMapping("/getMerchantStocks")
    public ResponseEntity getMerchantStocks() {
        return ResponseEntity.status(200).body(merchantStockService.getMerchantStocks());
    }

    // 2.3 Update(put)
    @PutMapping("/updateMerchantStock/{id}")
    public ResponseEntity updateMerchantStock(@PathVariable String id, @RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (merchantStockService.updateMerchantStock(id, merchantStock)) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Stock Updated."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant Stock Id Not Found."));
    }

    // 2.4 Delete
    @DeleteMapping("/deleteMerchantStock/{id}")
    public ResponseEntity deleteMerchantStock(@PathVariable String id) {
        if (merchantStockService.deleteMerchantStock(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Stock Deleted."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant Stock Id Not Found."));
    }

    // 3. Required endpoints
    // 3.1 addStock
    @PutMapping("/addStock/{merchant_id}/{product_id}/{amount}")
    public ResponseEntity addStock(@PathVariable String merchant_id, @PathVariable String product_id, @PathVariable int amount) {
        return ResponseEntity.status(200).body(new ApiResponse(merchantStockService.addStock(merchant_id, product_id, amount)));
    }

    // 3.2 buy
    @PutMapping("/buy/{user_id}/{product_id}/{merchant_id}")
    public ResponseEntity buy(@PathVariable String user_id, @PathVariable String product_id, @PathVariable String merchant_id) {
        return ResponseEntity.status(200).body(new ApiResponse(merchantStockService.buy(user_id, product_id, merchant_id)));
    }

    // 4. Extra 5 endpoints
    // ** Front end **
    // 4.1 wheelOfFortune
    @GetMapping("/displayWheelOfFortune")
    public ResponseEntity displayWheelOfFortune() {
        if (merchantStockService.displayWheelOfFortune() != null) {
            return ResponseEntity.status(200).body(merchantStockService.displayWheelOfFortune());
        }
        return ResponseEntity.status(400).body(new ApiResponse("No Products Available for the Wheel of Fortune."));
    }

    // ** Front end **
    // 4.2 Spin wheel
    @PutMapping("/spinWheel/{userId}")
    public ResponseEntity spinWheel(@PathVariable String userId) {
        if (merchantStockService.displayWheelOfFortune().isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("No Products Available to Spin."));
        }
        // User attempt to spin the wheel
        Product wonProduct = merchantStockService.spinWheel(userId);
        // Fails
        if (wonProduct == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Unable to Spin the Wheel. Either the User Has Already Spun or is Not Eligible."));
        }
        // Success
        return ResponseEntity.status(200).body(new ApiResponse("Congratulations! You won: " + wonProduct.getName()));
    }

    // 4.2 sales
    @PutMapping("/sales")
    public ResponseEntity sales() {
        if (merchantStockService.sales() != null) {
            return ResponseEntity.status(200).body(merchantStockService.sales());
        }
        return ResponseEntity.status(400).body(new ApiResponse("There Are No Products In Sales."));
    }
}
