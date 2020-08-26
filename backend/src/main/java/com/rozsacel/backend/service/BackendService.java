package com.rozsacel.backend.service;


import com.rozsacel.backend.domain.Attendance;
import com.rozsacel.backend.domain.User;
import com.rozsacel.backend.domain.WorkPlace;
import com.rozsacel.backend.dto.AttendanceDto;
import com.rozsacel.backend.dto.WagePerLocationDto;
import com.rozsacel.backend.repository.AttendanceRepo;
import com.rozsacel.backend.repository.UserRepo;
import com.rozsacel.backend.repository.WorkPlaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class BackendService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    AttendanceRepo attendanceRepo;

    @Autowired
    WorkPlaceRepo workPlaceRepo;

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        users = userRepo.findAll();
        for (User user : users) {
            user.setPassword("");
        }

        users.sort(Comparator.comparing(User::getName));
        return users;
    }

    public boolean doesUserExist(String userName) {
        User user = userRepo.findByUserName(userName);
        return user != null;
    }

    public User getUserData(String userName, String password) {
        User user = userRepo.findByUserNameAndPassword(userName, password);
        if(user == null){
            throw new IllegalArgumentException();
        }
        return user;
    }

    public List<AttendanceDto> getTimeSheet(int year, int month, int userId) {
        User user = userRepo.findById(userId).get();
        List<Attendance> attendances = attendanceRepo.findAllByUser(user);
        List<AttendanceDto> dtos = new ArrayList<>();
        for (Attendance attendance : attendances) {
            if (attendance.getDate().getMonthValue() == month+1 && attendance.getDate().getYear() == year) {
                AttendanceDto dto = new AttendanceDto();
                dto.index = attendance.getDate().getDayOfMonth() -1;
                dto.from = attendance.getStartTime().toString().substring(0, 5);
                dto.to = attendance.getEndTime().toString().substring(0, 5);
                dto.place = attendance.getWorkPlace().getName();
                dto.placeId = attendance.getWorkPlace().getId();
                dtos.add(dto);
            }
        }
        return dtos;
    }

    public List<WorkPlace> getAllWorkPlaces() {
        List<WorkPlace> workPlaces = workPlaceRepo.findAll();
        for (WorkPlace workPlace : workPlaces) {
            workPlace.setAttendance(null);
        }
        return workPlaces;
    }

    public void postAttendance(int userId, String from, String to, int placeId, int year, int month, int day){

        User user = userRepo.findById(userId).get();
        WorkPlace workPlace = workPlaceRepo.findById(placeId).get();
        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setWorkPlace(workPlace);
        attendance.setStartTime(LocalTime.parse(from));
        attendance.setEndTime(LocalTime.parse(to));
        LocalDate date = LocalDate.of(year,month,day);
        attendance.setDate(date);

        attendanceRepo.save(attendance);

    }

    public void deleteAttendance(int userId, int year, int month, int day){
        User user = userRepo.findById(userId).get();
        LocalDate date = LocalDate.of(year,month,day);

        Attendance attendance = attendanceRepo.findByUserAndDate(user,date);
        attendanceRepo.delete(attendance);
    }

    public void changePassword(int userId, String oldPass, String newPass){
        User user = userRepo.findById(userId).get();
        if(user.getPassword().equals(oldPass)){
            user.setPassword(newPass);
            userRepo.save(user);
        }else{
            throw new IllegalArgumentException();
        }
    }

    public void updateWorkPlace(WorkPlace workPlace){
        workPlaceRepo.save(workPlace);
    }

    //TODO
    public List<WagePerLocationDto> getWageOfEmployee(int year, int month, int id) {
        List<WagePerLocationDto> dtos = new ArrayList<>();
        User user = userRepo.findById(id). get();
        List<Attendance> attendances = attendanceRepo.findAllByUser(user);
        for (Attendance attendance : attendances) {
            if (attendance.getDate().getMonthValue() == month+1 && attendance.getDate().getYear() == year) {
                WagePerLocationDto dto = new WagePerLocationDto();

            }
        }

        return dtos;
    }
}
