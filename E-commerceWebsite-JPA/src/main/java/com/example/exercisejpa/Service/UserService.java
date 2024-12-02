package com.example.exercisejpa.Service;

import com.example.exercisejpa.Model.User;
import com.example.exercisejpa.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    // 1. Declare a dependency for UserRepository using Dependency Injection
    private final UserRepository userRepository;

    // 2. CRUD
    // 2.1 Get
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 2.2 Post
    public void addUser(User user) {
        userRepository.save(user);
    }

    // 2.3 Update
    public Boolean updateUser(Integer id, User user) {
        User oldUser = userRepository.getReferenceById(id);
        if (oldUser == null) {
            return false;
        }
        oldUser.setUsername(user.getUsername());
        oldUser.setPassword(user.getPassword());
        oldUser.setEmail(user.getEmail());
        oldUser.setRole(user.getRole());
        oldUser.setBalance(user.getBalance());
        userRepository.save(oldUser);
        return true;
    }

    // 2.4 Delete
    public Boolean deleteUser(Integer id) {
        User oldUser = userRepository.getReferenceById(id);
        if (oldUser == null) {
            return false;
        }
        userRepository.delete(oldUser);
        return true;
    }

    // Hellper method
    public boolean isUserExists(Integer userId) {
        // Check if the user exists
        for (User user : userRepository.findAll()) {
            if (user.getId().equals(userId)) {
                return true;
            }
        }
        return false;
    }
}