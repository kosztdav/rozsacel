package com.rozsacel.backend.service;


import com.rozsacel.backend.domain.User;
import com.rozsacel.backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BackendService {

    @Autowired
    UserRepo userRepo;

    public List<User> getAllUsers(){

        List<User> users = new ArrayList<>();
        users = userRepo.findAll();
        for (User user : users) {
            user.setPassword("");
        }

        return users;
    }

    public boolean doesUserExist(String userName){
        User user = userRepo.findByUserName(userName);
        return  user != null;
    }

    public User getUserData(String userName, String password){
        User user = userRepo.findByUserNameAndPassword(userName,password);
        user.setPassword("");
        return user;
    }
}
