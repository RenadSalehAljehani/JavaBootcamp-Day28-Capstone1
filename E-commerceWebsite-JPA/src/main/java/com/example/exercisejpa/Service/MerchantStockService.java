package com.example.exercisejpa.Service;

import com.example.exercisejpa.Model.MerchantStock;
import com.example.exercisejpa.Model.Product;
import com.example.exercisejpa.Model.User;
import com.example.exercisejpa.Repository.MerchantRepository;
import com.example.exercisejpa.Repository.MerchantStockRepository;
import com.example.exercisejpa.Repository.ProductRepository;
import com.example.exercisejpa.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    // 1. Declare a dependency for MerchantStockRepository using Dependency Injection
    private final MerchantStockRepository merchantStockRepository;

    // 2. Declare a dependency for MerchantRepository using Dependency Injection
    private final MerchantRepository merchantRepository;

    // 3. Declare a dependency for ProductRepository using Dependency Injection
    private final ProductRepository productRepository;

    // 4. Declares a final dependency for UserRepository sing Dependency Injection
    private final UserRepository userRepository;

    // 5. CRUD
    // 5.1 Get
    public List<MerchantStock> getAllMerchantStocks() {
        return merchantStockRepository.findAll();
    }

    // 5.2 Post
    public String addMerchantStock(MerchantStock merchantStock) {
        // Check if the merchant exists
        boolean merchantExists = merchantRepository.existsById(merchantStock.getMerchant_id());
        if (!merchantExists) {
            return "Merchant Not Found.";
        }

        // Check if the product exists
        boolean productExists = productRepository.existsById(merchantStock.getProduct_id());
        if (!productExists) {
            return "Product Not Found.";
        }

        // Add the new merchant stock if both exist
        merchantStockRepository.save(merchantStock);
        return "New Merchant Stock Added.";
    }

    // 5.3 Update
    public String updateMerchantStock(Integer id, MerchantStock merchantStock) {

        // Check if the merchant stock exists
        MerchantStock oldMerchantStock = merchantStockRepository.getReferenceById(id);
        if (oldMerchantStock == null) {
            return "Merchant Stock Not Found.";
        }

        // Check if the merchant exists
        boolean merchantExists = merchantRepository.existsById(merchantStock.getMerchant_id());
        if (!merchantExists) {
            return "Merchant Not Found.";
        }

        // Check if the product exists
        boolean productExists = productRepository.existsById(merchantStock.getProduct_id());
        if (!productExists) {
            return "Product Not Found.";
        }

        // Update the merchant stock if both exist
        oldMerchantStock.setMerchant_id(merchantStock.getMerchant_id());
        oldMerchantStock.setProduct_id(merchantStock.getProduct_id());
        oldMerchantStock.setStock(merchantStock.getStock());
        merchantStockRepository.save(oldMerchantStock);
        return "Merchant Stock Updated.";
    }

    // 5.4 Delete
    public Boolean deleteMerchantStock(Integer id) {
        MerchantStock oldMerchantStock = merchantStockRepository.getReferenceById(id);

        // Check if id exists
        if (oldMerchantStock == null) {
            return false;
        }
        merchantStockRepository.delete(oldMerchantStock);
        return true;
    }

    // 6. Required endpoints
    // 6.1 Create endpoint where merchant can add more stocks of product to a merchant stock
    public String addStock(Integer merchant_id, Integer product_id, int amount) {
        // Check if amount is valid
        if (amount <= 0) {
            return "Product amount should be a positive number larger than zero.";
        }
        for (MerchantStock merchantStock : merchantStockRepository.findAll()) {
            // Check if product and merchant exist
            if (merchantStock.getMerchant_id().equals(merchant_id) &&
                    merchantStock.getProduct_id().equals(product_id)) {
                // Update stock if both merchant_id and product_id exist
                merchantStock.setStock(merchantStock.getStock() + amount);
                return "Stock Successfully Updated.";
            }
            if (!merchantStock.getMerchant_id().equals(merchant_id) &&
                    !merchantStock.getProduct_id().equals(product_id)) {
                return "Both Product and Merchant Not Found.";
            }
            if (!merchantStock.getMerchant_id().equals(merchant_id)) {
                return "Merchant Not Found.";
            }
            if (!merchantStock.getProduct_id().equals(product_id)) {
                return "Product Not Found.";
            }
        }
        // If the loop completes without finding a match
        return "Something Went Wrong.";
    }


    // 6.2 Create endpoint where user can buy a product directly
    public String buy(Integer user_id, Integer product_id, Integer merchant_id) {
        // Check if user exist
        for (User user : userRepository.findAll()) {
            if (user.getId().equals(user_id)) {
                // Then check if product and merchant exist
                for (MerchantStock merchantStock : merchantStockRepository.findAll()) {
                    if (merchantStock.getProduct_id().equals(product_id) &&
                            merchantStock.getMerchant_id().equals(merchant_id)) {
                        // Check stock
                        if (merchantStock.getStock() > 0) {
                            for (Product product : productRepository.findAll()) {
                                if (product.getId().equals(product_id)) {
                                    // Check user balance
                                    if (user.getBalance() >= product.getPrice()) {
                                        user.setBalance(user.getBalance() - product.getPrice());
                                    } else {
                                        return "User Balance Not Enough.";
                                    }
                                }
                            }
                            merchantStock.setStock(merchantStock.getStock() - 1);
                            return "Purchase Completed Successfully."; // Product in stock and balance enough
                        } else {
                            return "Product Out of Stock.";
                        }
                    }
                    if (!merchantStock.getProduct_id().equals(product_id) &&
                            !merchantStock.getMerchant_id().equals(merchant_id)) {
                        return "Merchant and Product Not Found.";
                    }
                    if (!merchantStock.getProduct_id().equals(product_id)) {
                        return "Product Not Found.";
                    }
                    if (!merchantStock.getMerchant_id().equals(merchant_id)) {
                        return "Merchant Not Found.";
                    }
                }
            }
        }
        return "User Not Found.";
    }

    // 7. Extra 5 endpoints
    // Extra 5 endpoints: 1.1 Display wheel of fortune products > for products with high stock
    public ArrayList<String> displayWheelOfFortune() {
        // List of products to be displayed in the wheel of fortune
        ArrayList<String> wheelOfFortuneProductNames = new ArrayList<>();

        // Iterate over merchant stocks and find products with high stock
        for (MerchantStock merchantStock : merchantStockRepository.findAll()) {
            if (merchantStock.getStock() > 2000) { // Assumed value (should be based on company)
                // Find the matching product for this merchant stock
                for (Product product : productRepository.findAll()) {
                    if (product.getId().equals(merchantStock.getProduct_id())) {
                        wheelOfFortuneProductNames.add(product.getName()); // Add only the product name
                        break;
                    }
                }
            }
        }
        if (wheelOfFortuneProductNames.isEmpty()) {
            return null; // No products to display
        }
        return wheelOfFortuneProductNames; // Display wheel to the user
    }

    // Extra 5 endpoints: 1.2 Allow user to spin the wheel of fortune
    public Product spinWheel(Integer userId) {
        // Display wheel
        ArrayList<String> wheelOfFortuneProductNames = displayWheelOfFortune();

        // Check if user exist and is a customer
        for (User user : userRepository.findAll()) {
            if (user.getId().equals(userId)
                    && user.getRole().equalsIgnoreCase("Customer")) {
                if (user.getIsSpun()) {
                    return null; // User has already spun the wheel
                }
                // Select a random product name from the wheel
                Random random = new Random();
                String selectedProductName = wheelOfFortuneProductNames.get(random.nextInt(wheelOfFortuneProductNames.size()));

                // Find the corresponding Product object
                Product selectedProduct = null;
                for (Product product : productRepository.findAll()) {
                    if (product.getName().equalsIgnoreCase(selectedProductName)) {
                        selectedProduct = product;
                        break;
                    }
                }

                // Deduct stock for the selected product
                for (MerchantStock merchantStock : merchantStockRepository.findAll()) {
                    if (merchantStock.getProduct_id().equals(selectedProduct.getId())) {
                        merchantStock.setStock(merchantStock.getStock() - 1);
                        break; // Stock updated, exit loop
                    }
                }
                user.setIsSpun(true); // Mark the user as having spun the wheel
                return selectedProduct; // Return the product won by the user
            }
        }
        return null; // User not found or not a customer
    }

    // Extra 5 endpoints: 2. sales > for products with high stock
    public ArrayList<Product> sales() {
        // List of products in sales
        ArrayList<Product> productsInSales = new ArrayList<>();

        // Iterate over products and filter those that meet the sales criteria
        for (Product product : productRepository.findAll()) {
            // Check if the product is in merchant stock and has sufficient stock
            boolean productInStock = false;
            for (MerchantStock merchantStock : merchantStockRepository.findAll()) {
                if (merchantStock.getProduct_id().equals(product.getId()) && merchantStock.getStock() > 2000) {
                    productInStock = true;
                    break; // Stop after finding one match, no need to continue checking other merchant stocks
                }
            }

            // If the product meets the conditions, apply the discount
            if (productInStock) {
                // Apply the discount (half the price)
                product.setPrice(Math.round((product.getPrice() / 2) * 100.0) / 100.0);
                productsInSales.add(product); // Add product to sales list
            }
        }
        if (productsInSales.isEmpty()) {
            return null; // No products in sales
        }
        return productsInSales; // Return the list of products on sale
    }
}