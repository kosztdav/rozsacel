package com.rozsacel.backend.controller;

import com.rozsacel.backend.domain.User;
import com.rozsacel.backend.domain.WorkPlace;
import com.rozsacel.backend.dto.AttendanceDto;
import com.rozsacel.backend.dto.WagePerLocationDto;
import com.rozsacel.backend.service.BackendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
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
        return service.getUserData(userName, password);
    }

    @RequestMapping(path = "/getTimeSheet", method = GET, produces = "application/json")
    @ResponseBody
    public List<AttendanceDto> getTimeSheet(@RequestParam int year, @RequestParam int month, @RequestParam int userId) {
        return service.getTimeSheet(year, month, userId);
    }

    @RequestMapping(path = "/getAllWorkPlaces", method = GET, produces = "application/json")
    @ResponseBody
    public List<WorkPlace> getAllWorkPlaces() {
        return service.getAllWorkPlaces();
    }

    @RequestMapping(path = "/postAttendance", method = POST, produces = "application/json")
    @ResponseBody
    public String postAttendance(@RequestParam int userId, @RequestParam String from, @RequestParam String to, @RequestParam int placeId, @RequestParam int year, @RequestParam int month, @RequestParam int day) {
        service.postAttendance(userId, from, to, placeId, year, month, day);
        return "OK";
    }

    @RequestMapping(path = "/deleteAttendance", method = POST, produces = "application/json")
    @ResponseBody
    public String deleteAttendance(@RequestParam int userId, @RequestParam int year, @RequestParam int month, @RequestParam int day) {
        service.deleteAttendance(userId, year, month, day);
        return "OK";
    }

    @RequestMapping(path = "/changePassword", method = POST, produces = "application/json")
    @ResponseBody
    public String changePassword(@RequestParam int userId, @RequestParam String oldPass, @RequestParam String newPass) {
        service.changePassword(userId, oldPass, newPass);
        return "OK";
    }

    @RequestMapping(path = "/updateWorkPlace", method = POST, produces = "application/json")
    @ResponseBody
    public String updateWorkPlace(@RequestBody WorkPlace workPlace) {
        service.updateWorkPlace(workPlace);
        return "OK";
    }

    @RequestMapping(path = "/newWorkPlace", method = POST, produces = "application/json")
    @ResponseBody
    public String newWorkPlace(@RequestParam String name, @RequestParam boolean isActive) {
        service.newWorkPlace(name, isActive);
        return "OK";
    }

    @RequestMapping(path = "/getWage/{year}/{month}/{id}", method = GET, produces = "application/json")
    @ResponseBody
    public List<WagePerLocationDto> getWageOfEmployee(@PathVariable int year, @PathVariable int month, @PathVariable int id) {
        return service.getWageOfEmployee(year, month, id);
    }
}
