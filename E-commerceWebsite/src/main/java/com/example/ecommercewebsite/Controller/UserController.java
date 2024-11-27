package com.example.ecommercewebsite.Controller;

import com.example.ecommercewebsite.ApiResponse.ApiResponse;
import com.example.ecommercewebsite.Model.User;
import com.example.ecommercewebsite.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    // 1. Declares a final dependency for UserService, used to perform operations related to users
    private final UserService userService;

    // 2. CRUD endpoints
    // 2.1 Create(post)
    @PostMapping("/addUser")
    public ResponseEntity addUser(@RequestBody @Valid User user, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("New User Added."));
    }

    // 2.2 Read(get)
    @GetMapping("/getUsers")
    public ResponseEntity getUsers(){
        return ResponseEntity.status(200).body(userService.getUsers());
    }

    // 2.3 Update(put)
    @PutMapping("/updateUser/{id}")
    public ResponseEntity updateUser(@PathVariable String id, @RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(userService.updateUser(id,user)){
            return ResponseEntity.status(200).body(new ApiResponse("User Updated."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User Id Not Found."));
    }

    // 2.4 Delete
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity deleteUser(@PathVariable String id){
        if(userService.deleteUser(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("User Deleted."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User Id Not Found."));
    }
}
