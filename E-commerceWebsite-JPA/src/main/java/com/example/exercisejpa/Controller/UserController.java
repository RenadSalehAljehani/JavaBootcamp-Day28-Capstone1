package com.example.exercisejpa.Controller;

import com.example.exercisejpa.ApiResponse.ApiResponse;
import com.example.exercisejpa.Model.User;
import com.example.exercisejpa.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    // 1. Declare a dependency for UserService using Dependency Injection
    private final UserService userService;

    // 2. CRUD
    // 2.1 Get
    @GetMapping("/get")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    // 2.2 Post
    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("New User Added."));
    }

    // 2.3 Update
    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id, @RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (userService.updateUser(id, user)) {
            return ResponseEntity.status(200).body(new ApiResponse("User Updated."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User Not Found."));
    }

    // 2.4 Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("User Deleted."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User Not Found."));
    }
}