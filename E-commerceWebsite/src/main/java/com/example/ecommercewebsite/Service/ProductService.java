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

    // 3. CRUD endpoints
    // 3.1 Create(post)
    public boolean addProduct(Product product) {
        for (Category category:categoryService.getCategories()){
            if(product.getCategoryID().equalsIgnoreCase(category.getId())){
                products.add(product);
                return true;
            }
        }
        return false;
    }

    // 3.2 Read(get)
    public ArrayList<Product> getProducts() {
        return products;
    }

    // 3.3 Update(put)
    public boolean updateProduct(String id, Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equalsIgnoreCase(id)) {
                products.set(i, product);
                return true;
            }
        }
        return false;
    }

    // 3.4 Delete
    public boolean deleteProduct(String id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equalsIgnoreCase(id)) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    // 4. Extra 5 endpoints
    // Extra 5 endpoints: 5. gift finder
    public ArrayList<Product> giftFinder(int minAge, int maxAge, String category){
        // List of suggested gifts
        ArrayList<Product> suggestedGifts = new ArrayList<>();

        // Iterate over products
        for (Product product: products){
            // Find the category for the product
            for (Category category1: categoryService.getCategories()) {
                // Check if the category matches and the age range fits
                if (product.getCategoryID().equalsIgnoreCase(category1.getId())
                        && category1.getName().equalsIgnoreCase(category)
                        && category1.getAge() >= minAge
                        && category1.getAge() <= maxAge) {
                    suggestedGifts.add(product);
                    break;
                }
            }
        }
        if(suggestedGifts.isEmpty()){
            return null;
        }
        return suggestedGifts;
    }
}