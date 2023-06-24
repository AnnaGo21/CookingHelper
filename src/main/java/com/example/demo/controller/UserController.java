package com.example.demo.controller;

//import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
//    @PostMapping("/addUser")
//    public User postDetails(User user){
//        return userService.saveDetails(user);
//    }

    @GetMapping("/user")
    public String getUser(){
        return "User added. Hello, user!";
    }

    @GetMapping("/add")
    public String addUser(){
        return "User added!";
    }

    @GetMapping("/remove")
    public String removedUser(@RequestParam int id){
        return "User number " + id + " was removed";
    }
}

