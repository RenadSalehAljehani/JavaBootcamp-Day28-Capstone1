package com.example.ecommercewebsite.Service;

import com.example.ecommercewebsite.Model.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;


@Service
@AllArgsConstructor
public class MerchantStockService {

    // 1. Create list of merchantStocks
    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    // 2. Declares a final dependency for ProductService, used to perform operations related to products
    private final ProductService productService;

    // 3. Declares a final dependency for MerchantService, used to perform operations related to merchants
    private final MerchantService merchantService;

    // 4. Declares a final dependency for UserService, used to perform operations related to users
    private final UserService userService;

    // 5. CRUD endpoints
    // 5.1 Create(post)
    public String addMerchantStock(MerchantStock merchantStock) {
        boolean merchantFound = false;
        boolean productFound = false;

        // Check if the merchant exists
        for (Merchant merchant : merchantService.getMerchants()) {
            if (merchantStock.getMerchant_id().equalsIgnoreCase(merchant.getId())) {
                merchantFound = true;
                break; // Merchant found, no need to check further
            }
        }

        // Check if the product exists
        for (Product product : productService.getProducts()) {
            if (merchantStock.getProduct_id().equalsIgnoreCase(product.getId())) {
                productFound = true;
                break; // Product found, no need to check further
            }
        }

        // Determine the result based on the checks
        if (!merchantFound && !productFound) {
            return "Both Product and Merchant Not Found.";
        } else if (!merchantFound) {
            return "Merchant Not Found.";
        } else if (!productFound) {
            return "Product Not Found.";
        }

        // If both merchant and product are found, add the stock
        merchantStocks.add(merchantStock);
        return "New Merchant Stock Added.";
    }

    // 5.2 Read(get)
    public ArrayList<MerchantStock> getMerchantStocks() {
        return merchantStocks;
    }

    // 5.3 Update(put)
    public boolean updateMerchantStock(String id, MerchantStock merchantStock) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equalsIgnoreCase(id)) {
                merchantStocks.set(i, merchantStock);
                return true;
            }
        }
        return false;
    }

    // 5.4 Delete
    public boolean deleteMerchantStock(String id) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equalsIgnoreCase(id)) {
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }

    // 6. Required endpoints
    // 6.1 Create endpoint where merchant can add more stocks of product to a merchant stock
    public String addStock(String merchant_id, String product_id, int amount) {
        // Check if amount is valid
        if (amount <= 0) {
            return "Product Amount Should Be a Positive Number Larger Than Zero.";
        }
        for (MerchantStock merchantStock : merchantStocks) {
            // Check if product and merchant exist
            if (merchantStock.getMerchant_id().equalsIgnoreCase(merchant_id) &&
                    merchantStock.getProduct_id().equalsIgnoreCase(product_id)) {
                // Update stock if both merchant_id and product_id exist
                merchantStock.setStock(merchantStock.getStock() + amount);
                return "Stock Successfully Updated.";
            }
            if (!merchantStock.getMerchant_id().equalsIgnoreCase(merchant_id) &&
                    !merchantStock.getProduct_id().equalsIgnoreCase(product_id)) {
                return "Both Product and Merchant Not Found.";
            }
            if (!merchantStock.getMerchant_id().equalsIgnoreCase(merchant_id)) {
                return "Merchant Not Found.";
            }
            if (!merchantStock.getProduct_id().equalsIgnoreCase(product_id)) {
                return "Product Not Found.";
            }
        }
        // If the loop completes without finding a match
        return "Something Went Wrong.";
    }

    // 6.2 Create endpoint where user can buy a product directly
    public String buy(String user_id, String product_id, String merchant_id) {
        // Check if user exist
        for (User user : userService.getUsers()) {
            if (user.getId().equalsIgnoreCase(user_id)) {
                // Then check if product and merchant exist
                for (MerchantStock merchantStock : merchantStocks) {
                    if (merchantStock.getProduct_id().equalsIgnoreCase(product_id) &&
                            merchantStock.getMerchant_id().equalsIgnoreCase(merchant_id)) {
                        // Check stock
                        if (merchantStock.getStock() > 0) {
                            for (Product product : productService.getProducts()) {
                                if (product.getId().equalsIgnoreCase(product_id)) {
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
                    if (!merchantStock.getProduct_id().equalsIgnoreCase(product_id) &&
                            !merchantStock.getMerchant_id().equalsIgnoreCase(merchant_id)) {
                        return "Merchant and Product Not Found.";
                    }
                    if (!merchantStock.getProduct_id().equalsIgnoreCase(product_id)) {
                        return "Product Not Found.";
                    }
                    if (!merchantStock.getMerchant_id().equalsIgnoreCase(merchant_id)) {
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
        for (MerchantStock merchantStock : merchantStocks) {
            if (merchantStock.getStock() > 2000) { // Assumed value (should be based on company)
                // Find the matching product for this merchant stock
                for (Product product : productService.getProducts()) {
                    if (product.getId().equalsIgnoreCase(merchantStock.getProduct_id())) {
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
    public Product spinWheel(String userId) {
        // Display wheel
        ArrayList<String> wheelOfFortuneProductNames = displayWheelOfFortune();

        // Check if user exist and is a customer
        for (User user : userService.getUsers()) {
            if (user.getId().equalsIgnoreCase(userId)
                    && user.getRole().equalsIgnoreCase("Customer")) {
                if (user.isSpun()) {
                    return null; // User has already spun the wheel
                }
                // Select a random product name from the wheel
                Random random = new Random();
                String selectedProductName = wheelOfFortuneProductNames.get(random.nextInt(wheelOfFortuneProductNames.size()));

                // Find the corresponding Product object
                Product selectedProduct = null;
                for (Product product : productService.getProducts()) {
                    if (product.getName().equalsIgnoreCase(selectedProductName)) {
                        selectedProduct = product;
                        break;
                    }
                }

                // Deduct stock for the selected product
                for (MerchantStock merchantStock : merchantStocks) {
                    if (merchantStock.getProduct_id().equalsIgnoreCase(selectedProduct.getId())) {
                        merchantStock.setStock(merchantStock.getStock() - 1);
                        break; // Stock updated, exit loop
                    }
                }
                user.setSpun(true); // Mark the user as having spun the wheel
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
        for (Product product : productService.getProducts()) {
            // Check if the product is in merchant stock and has sufficient stock
            boolean productInStock = false;
            for (MerchantStock merchantStock : merchantStocks) {
                if (merchantStock.getProduct_id().equalsIgnoreCase(product.getId()) && merchantStock.getStock() > 2000) {
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
