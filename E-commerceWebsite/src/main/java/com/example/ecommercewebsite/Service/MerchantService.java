package com.example.ecommercewebsite.Service;

import com.example.ecommercewebsite.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {

    // 1. Crete list of merchants
    static ArrayList<Merchant> merchants = new ArrayList<>();

    // 2. CRUD endpoints
    // 2.1 Create(post)
    public void addMerchant(Merchant merchant){
        merchants.add(merchant);
    }

    // 2.2 Read(get)
    public ArrayList<Merchant> getMerchants(){
        return merchants;
    }

    // 2.3 Update(put)
    public boolean updateMerchant(String id, Merchant merchant){
        for (int i = 0; i < merchants.size(); i++) {
            if(merchants.get(i).getId().equalsIgnoreCase(id)){
                merchants.set(i,merchant);
                return true;
            }
        }
        return false;
    }

    // 2.4 Delete
    public boolean deleteMerchant(String id){
        for (int i = 0; i < merchants.size(); i++) {
            if(merchants.get(i).getId().equalsIgnoreCase(id)){
                merchants.remove(i);
                return true;
            }
        }
        return false;
    }
}