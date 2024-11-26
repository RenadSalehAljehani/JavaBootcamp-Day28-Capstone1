package com.example.ecommercewebsite.Controller;

import com.example.ecommercewebsite.ApiResponse.ApiResponse;
import com.example.ecommercewebsite.Model.Merchant;
import com.example.ecommercewebsite.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    // 1. Declares a final dependency for MerchantService, used to perform operations related to merchants
    private final MerchantService merchantService;

    // 2. CRUD endpoints
    // 2.1 Create(post)
    @PostMapping("/addMerchant")
    public ResponseEntity addMerchant(@RequestBody @Valid Merchant merchant, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        merchantService.addMerchant(merchant);
        return ResponseEntity.status(200).body(new ApiResponse("New Merchant Added."));
    }

    // 2.2 Read(get)
    @GetMapping("/getMerchants")
    public ResponseEntity getMerchants(){
        return ResponseEntity.status(200).body(merchantService.getMerchants());
    }

    // 2.3 Update(put)
    @PutMapping("/updateMerchant/{id}")
    public ResponseEntity updateMerchant(@PathVariable String id, @RequestBody @Valid Merchant merchant, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(merchantService.updateMerchant(id,merchant)){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Updated."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant Id Not Found."));
    }

    // 2.4 Delete
    @DeleteMapping("/deleteMerchant/{id}")
    public ResponseEntity deleteMerchant(@PathVariable String id){
        if(merchantService.deleteMerchant(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Deleted."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant Id Not Found."));
    }
}