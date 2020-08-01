package com.rozsacel.backend.controller;


import com.rozsacel.backend.domain.User;
import com.rozsacel.backend.service.BackendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    BackendService service;

    @RequestMapping(path = "", method = GET, produces = "application/json")
    @ResponseBody
    public String home() {
        return "Home";
    }

    @RequestMapping(path = "/users", method = GET, produces = "application/json")
    @ResponseBody
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @RequestMapping(path = "/checkUser/{userName}", method = GET, produces = "application/json")
    @ResponseBody
    public boolean doesUserExist(@PathVariable String userName) {
        return service.doesUserExist(userName);
    }

    @RequestMapping(path = "/login", method = GET, produces = "application/json")
    @ResponseBody
    public User getUserData(@RequestParam String userName, @RequestParam String password) {
        return service.getUserData(userName,password);
    }

}
