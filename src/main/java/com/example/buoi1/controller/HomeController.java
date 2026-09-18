package com.example.buoi1.controller;

import com.example.buoi1.model.Users;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HomeController {

    // Khởi tạo danh sách mẫu lưu trữ trong bộ nhớ
    private final List<Users> userList = new ArrayList<>();

    // Khối khởi tạo dữ liệu ban đầu (Constructor)
    public HomeController() {
        userList.add(new Users(1, "NgoAhnDuc"));
        userList.add(new Users(2, "TranVanA"));
    }

    @GetMapping("/test")
    public String test() {
        return "REST API OK";
    }

    @GetMapping("/users")
    public Users users() {
        return new Users(1, "NgoAhnDuc");
    }

    @GetMapping("/list-users")
    public List<Users> listUsers() {
        // Trả về danh sách người dùng thay vì gọi lại chính hàm này
        return userList;
    }

    @PostMapping("/add-user")
    public String addUser(@RequestBody Users user) {
        userList.add(user);
        return "Add user success: " + user.getName();
    }
    @PostMapping("/update")
    public String updateUser(@RequestBody Users user, @RequestParam("id") int id) {
        for(Users users : userList) {
            if(users.getId().equals(id)) {
                users.setName(user.getName());
            }
        }
        return "ok";
    }
    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") int id) {
        for(Users users : userList) {
            if(users.getId().equals(id)) {
                userList.remove(users);
                break;
            }
        }
        return "ok";}
}