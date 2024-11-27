package com.example.ecommercewebsite.Service;

import com.example.ecommercewebsite.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    // 1. Crete list of users
    static ArrayList<User> users = new ArrayList<>();

    // 2. CRUD endpoints
    // 2.1 Create(post)
    public void addUser(User user) {
        users.add(user);
    }

    // 2.2 Read(get)
    public ArrayList<User> getUsers() {
        return users;
    }

    // 2.3 Update(put)
    public boolean updateUser(String id, User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equalsIgnoreCase(id)) {
                users.set(i, user);
                return true;
            }
        }
        return false;
    }

    // 2.4 Delete
    public boolean deleteUser(String id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equalsIgnoreCase(id)) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }

   // Hellper method
    public boolean isUserExists(String userId) {
        for (User user : users) {
            if (user.getId().equalsIgnoreCase(userId)) {
                return true;
            }
        }
        return false;
    }
}
