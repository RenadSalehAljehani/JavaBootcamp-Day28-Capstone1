package com.example.ecommercewebsite.Service;

import com.example.ecommercewebsite.Model.Category;
import com.example.ecommercewebsite.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {

    // 1. Create list of products
    static ArrayList<Product> products = new ArrayList<>();

    // 2. Declares a final dependency for CategoryService, used to perform operations related to categories
    private final CategoryService categoryService;

    // 3. Declares a final dependency for UserService, used to perform operations related to users
    private final UserService userService;

    // 4. CRUD endpoints
    // 4.1 Create(post)
    public boolean addProduct(Product product) {
        for (Category category:categoryService.getCategories()){
            if(product.getCategoryID().equalsIgnoreCase(category.getId())){
                products.add(product);
                return true;
            }
        }
        return false;
    }

    // 4.2 Read(get)
    public ArrayList<Product> getProducts() {
        return products;
    }

    // 4.3 Update(put)
    public boolean updateProduct(String id, Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equalsIgnoreCase(id)) {
                products.set(i, product);
                return true;
            }
        }
        return false;
    }

    // 4.4 Delete
    public boolean deleteProduct(String id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equalsIgnoreCase(id)) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }

   // ** New endpoint **
    // 5. Extra 5 endpoints
    // Extra 5 endpoints: 4. Allow user to search for products within his/her budget
    public ArrayList<Product> getProductsWithinBudget(String userId) {
        // List of suggested products
        ArrayList<Product> suggestedProducts = new ArrayList<>();

        // Call endpoint to calculate user categories budgets
        ArrayList<Double> userCategoriesBudgets = categoryService.calculateUserCategoriesBudgets(userId);

        // Flag to check if the user exists
        boolean userFound = false;

        // Check if user exist
        for (User user : userService.getUsers()) {
            if (user.getId().equalsIgnoreCase(userId)) {
                // Iterator index to keep track of budgets
                int budgetIndex = 0;

                // Iterate over categories and their budgets
                for (Category category : categoryService.getCategories()) {
                    double userBudgetForCategory = userCategoriesBudgets.get(budgetIndex);
                    budgetIndex++;

                    // Check products for the current category
                    for (Product product : products) {
                        if (product.getCategoryID().equalsIgnoreCase(category.getId())) {
                            // Check if the product's price is within the allocated budget for that category
                            if (product.getPrice() <= userBudgetForCategory) {
                                suggestedProducts.add(product);
                            }
                        }
                    }
                }
                if (suggestedProducts.isEmpty()) {
                    return null; // No suggested products within user budget
                }
                return suggestedProducts; // Return the list of suggested products
            }
        }
        // If the user was not found, return null
        if (!userFound) {
            return null; // User not found
        }
        return null; // should not be reached
    }

    // Extra 5 endpoints: 5. gift finder > to search for gifts by age and within user budget in the specified category
    public ArrayList<Product> giftFinder(String userId, String categoryId, int recipientAge) {

        // Get the list of products within the user's budget for all categories
        ArrayList<Product> productsWithinBudget = getProductsWithinBudget(userId);

        if (productsWithinBudget == null) {
            return null; // No products found within the user's budget
        }

        // List to store suggested gifts
        ArrayList<Product> suggestedGifts = new ArrayList<>();

        // Loop through products within user budget for all categories
        for (Product product : productsWithinBudget) {
            // Find products within the specified category and age
            if (product.getCategoryID().equalsIgnoreCase(categoryId)
                    && product.getAge() == recipientAge) {
                suggestedGifts.add(product);
            }
        }
        if (suggestedGifts.isEmpty()) {
            return null;
        }
        return suggestedGifts;
    }
}
