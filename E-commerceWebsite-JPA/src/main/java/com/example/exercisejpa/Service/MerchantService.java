package com.example.exercisejpa.Service;

import com.example.exercisejpa.Model.Merchant;
import com.example.exercisejpa.Repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {

    // 1. Declare a dependency for MerchantRepository using Dependency Injection
    private final MerchantRepository merchantRepository;

    // 2.CRUD
    // 2.1 Get
    public List<Merchant> getAllMerchants() {
        return merchantRepository.findAll();
    }

    // 2.2 Post
    public void addMerchant(Merchant merchant) {
        merchantRepository.save(merchant);
    }

    // 2.3 Update
    public Boolean updateMerchant(Integer id, Merchant merchant) {
        Merchant oldMerchant = merchantRepository.getReferenceById(id);
        // Check if id exists
        if (oldMerchant == null) {
            return false;
        }
        oldMerchant.setName(merchant.getName());
        merchantRepository.save(oldMerchant);
        return true;
    }

    // 2.4 Delete
    public Boolean deleteMerchant(Integer id) {
        Merchant oldMerchant = merchantRepository.getReferenceById(id);
        // Check if id exists
        if (oldMerchant == null) {
            return false;
        }
        merchantRepository.delete(oldMerchant);
        return true;
    }
}