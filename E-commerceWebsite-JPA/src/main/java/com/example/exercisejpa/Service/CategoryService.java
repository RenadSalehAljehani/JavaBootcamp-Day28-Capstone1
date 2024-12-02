package com.example.exercisejpa.Service;

import com.example.exercisejpa.Model.Category;
import com.example.exercisejpa.Model.User;
import com.example.exercisejpa.Repository.CategoryRepository;
import com.example.exercisejpa.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    // 1. Declare a dependency for CategoryRepository using Dependency Injection
    private final CategoryRepository categoryRepository;

    // 2. Declares a final dependency for UserService, using Dependency Injection
    private final UserRepository userRepository;

    // 3. CRUD
    // 3.1 Get
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // 3.2 Post
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    // 3.3 Update
    public Boolean updateCategory(Integer id, Category category) {
        Category oldCategory = categoryRepository.getReferenceById(id);
        // Check if id exists
        if (oldCategory == null) {
            return false;
        }
        oldCategory.setName(category.getName());
        categoryRepository.save(oldCategory);
        return true;
    }

    // 3.4 Delete
    public Boolean deleteCategory(Integer id) {
        Category oldCategory = categoryRepository.getReferenceById(id);
        // Check if id exists
        if (oldCategory == null) {
            return false;
        }
        categoryRepository.delete(oldCategory);
        return true;
    }

    // ** New endpoint **
    // 4. Extra 5 endpoints
    // Extra 5 endpoints: 3. Calculate budget allocations for user across all categories
    public ArrayList<Double> calculateUserCategoriesBudgets(Integer userId) {
        // Create a list to store categories budgets user
        ArrayList<Double> userCategoriesBudgets = new ArrayList<>();

        // Check if user exist
        for (User user : userRepository.findAll()) {
            if (user.getId().equals(userId)) {
                // Loop through all categories and calculate the budget for this user in each category
                for (Category category : categoryRepository.findAll()) {
                    Double allocatedBudget = (category.getAllocationPercentage() / 100) * user.getTotalBudget();
                    userCategoriesBudgets.add(allocatedBudget); // Add the allocated budget for each category
                }
            }
        }
        return userCategoriesBudgets;
    }

    // Hellper method
    public boolean isCategoryExists(Integer categoryId) {
        // Check if the category exists
        for (Category category : categoryRepository.findAll()) {
            if (category.getId().equals(categoryId)) {
                return true;
            }
        }
        return false;
    }
}