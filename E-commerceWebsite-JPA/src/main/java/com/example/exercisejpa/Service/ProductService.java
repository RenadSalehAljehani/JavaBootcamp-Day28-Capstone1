package com.example.exercisejpa.Service;

import com.example.exercisejpa.Model.Category;
import com.example.exercisejpa.Model.Product;
import com.example.exercisejpa.Model.User;
import com.example.exercisejpa.Repository.CategoryRepository;
import com.example.exercisejpa.Repository.ProductRepository;
import com.example.exercisejpa.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    // 1. Declare a dependency for ProductRepository using Dependency Injection
    private final ProductRepository productRepository;

    // 2. Declare a dependency for CategoryRepository using Dependency Injection
    private final CategoryRepository categoryRepository;

    // 3. Declare a dependency for CategoryService using Dependency Injection
    private final CategoryService categoryService;

    // 4. Declares a final dependency for UserRepository using Dependency Injection
    private final UserRepository userRepository;

    // 5. CRUD
    // 5.1 Get
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 5.2 Post
    public Boolean addProduct(Product product) {
        // Check if the category exists
        boolean categoryExists = categoryRepository.existsById(product.getCategoryID());
        if (!categoryExists) {
            return false;
        }
        productRepository.save(product);
        return true;
    }

    // 5.3 Update
    public String updateProduct(Integer id, Product product) {
        // Check if the product exists
        Product oldProduct = productRepository.getReferenceById(id);
        if (oldProduct == null) {
            return "Product Not Found.";
        }

        // Check if the category exists
        boolean categoryExists = categoryRepository.existsById(product.getCategoryID());
        if (!categoryExists) {
            return "Category Not Found.";
        }

        // Update the product
        oldProduct.setName(product.getName());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setCategoryID(product.getCategoryID());
        productRepository.save(oldProduct);
        return "Product Updated.";
    }

    // 5.4 Delete
    public Boolean deleteProduct(Integer id) {
        Product oldProduct = productRepository.getReferenceById(id);
        // Check if id exists
        if (oldProduct == null) {
            return false;
        }
        productRepository.delete(oldProduct);
        return true;
    }

    // ** New endpoint **
    // 6. Extra 5 endpoints
    // Extra 5 endpoints: 4. Allow user to search for products within his/her budget
    public ArrayList<Product> getProductsWithinBudget(Integer userId) {
        // List of suggested products
        ArrayList<Product> suggestedProducts = new ArrayList<>();

        // Call endpoint to calculate user categories budgets
        ArrayList<Double> userCategoriesBudgets = categoryService.calculateUserCategoriesBudgets(userId);

        // Flag to check if the user exists
        boolean userFound = false;

        // Check if user exist
        for (User user : userRepository.findAll()) {
            if (user.getId().equals(userId)) {
                // Iterator index to keep track of budgets
                int budgetIndex = 0;

                // Iterate over categories and their budgets
                for (Category category : categoryRepository.findAll()) {
                    double userBudgetForCategory = userCategoriesBudgets.get(budgetIndex);
                    budgetIndex++;

                    // Check products for the current category
                    for (Product product : productRepository.findAll()) {
                        if (product.getCategoryID().equals(category.getId())) {
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
    public ArrayList<Product> giftFinder(Integer userId, Integer categoryId, int recipientAge) {

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
            if (product.getCategoryID().equals(categoryId)
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