package com.example.exercisejpa.Controller;

import com.example.exercisejpa.ApiResponse.ApiResponse;
import com.example.exercisejpa.Model.Merchant;
import com.example.exercisejpa.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    // 1. Declare a dependency for MerchantService using Dependency Injection
    private final MerchantService merchantService;

    // 2. CRUD
    // 2.1 Get
    @GetMapping("/get")
    public ResponseEntity getAllMerchants() {
        return ResponseEntity.status(200).body(merchantService.getAllMerchants());
    }

    // 2.2 Post
    @PostMapping("/add")
    public ResponseEntity addMerchant(@RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        merchantService.addMerchant(merchant);
        return ResponseEntity.status(200).body(new ApiResponse("New Merchant Added."));
    }

    // 2.3 Update
    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchant(@PathVariable Integer id, @RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (merchantService.updateMerchant(id, merchant)) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Updated."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant Not Found."));
    }

    // 2.4 Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchant(@PathVariable Integer id) {
        if (merchantService.deleteMerchant(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Deleted."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant Not Found."));
    }
}