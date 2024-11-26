package com.example.ecommercewebsite.Service;

import com.example.ecommercewebsite.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {

    // 1. Create list of categories
    static ArrayList<Category> categories = new ArrayList<>();

    // 2. CRUD endpoints
    // 2.1 Create(post)
    public void addCategory(Category category) {
        categories.add(category);
    }

    // 2.2 Read(get)
    public ArrayList<Category> getCategories() {
        return categories;
    }

    // 2.3 Update(put)
    public boolean updateCategory(String id, Category category) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equalsIgnoreCase(id)) {
                categories.set(i, category);
                return true;
            }
        }
        return false;
    }

    // 2.4 Delete
    public boolean deleteCategory(String id) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equalsIgnoreCase(id)) {
                categories.remove(i);
                return true;
            }
        }
        return false;
    }
}