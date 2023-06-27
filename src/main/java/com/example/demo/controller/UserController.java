//package com.example.demo.controller;
//
//import com.example.demo.entity.User;
//import com.example.demo.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/login")
//public class UserController {
//
//    private final UserService userService;
//
//    @Autowired
//    public UserController(UserService userService){
//        this.userService = userService;
//    }
//
//    @GetMapping("/user/{id}")
//    public User getUserById(@PathVariable int id){
//        return userService.getUserById(id);
//    }
//
//    @PostMapping("/addUser")
//    public User postDetails(@RequestBody User user){
//        return userService.saveDetails(user);
//    }
//
//
////    @GetMapping("/user")
////    public User getUserById(@RequestParam int id){
////        return userService.getUserById(id);
////    }
//
////    @PostMapping("/addUser")
////    public User postDetails(User user){
////        return userService.saveDetails(user);
////    }
////
////    @GetMapping("/user")
////    public String getUser(){
////        return "User added. Hello, user!";
////    }
//
//}
//
