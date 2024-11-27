package com.example.ecommercewebsite.Service;

import com.example.ecommercewebsite.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {

    // 1. Create list of categories
    static ArrayList<Category> categories = new ArrayList<>();

    // 2. Declares a final dependency for UserService, used to perform operations related to users
    private final UserService userService;

    // 3. CRUD endpoints
    // 3.1 Create(post)
    public void addCategory(Category category) {
        categories.add(category);
    }

    // 3.2 Read(get)
    public ArrayList<Category> getCategories() {
        return categories;
    }

    // 3.3 Update(put)
    public boolean updateCategory(String id, Category category) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equalsIgnoreCase(id)) {
                categories.set(i, category);
                return true;
            }
        }
        return false;
    }

    // 3.4 Delete
    public boolean deleteCategory(String id) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equalsIgnoreCase(id)) {
                categories.remove(i);
                return true;
            }
        }
        return false;
    }

    // ** New endpoint **
    // 4. Extra 5 endpoints
    // Extra 5 endpoints: 2. Calculate budget allocations for user across all categories
    public ArrayList<Double> calculateUserCategoriesBudgets(String userId) {
        // Create a list to store categories budgets user
        ArrayList<Double> userCategoriesBudgets = new ArrayList<>();

        // Check if user exist
        for (User user : userService.getUsers()) {
            if (user.getId().equalsIgnoreCase(userId)) {
                // Loop through all categories and calculate the budget for this user in each category
                for (Category category : categories) {
                    double allocatedBudget = (category.getAllocationPercentage() / 100) * user.getTotalBudget();
                    userCategoriesBudgets.add(allocatedBudget); // Add the allocated budget for each category
                }
            }
        }
        return userCategoriesBudgets;
    }

    // Hellper method
    public boolean isCategoryExists(String categoryId) {
        for (Category category: categories) {
            if (category.getId().equalsIgnoreCase(categoryId)) {
                return true;
            }
        }
        return false;
    }
}
