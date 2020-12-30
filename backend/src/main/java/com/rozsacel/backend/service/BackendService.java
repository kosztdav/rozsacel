package com.rozsacel.backend.service;


import com.rozsacel.backend.domain.Advance;
import com.rozsacel.backend.domain.Attendance;
import com.rozsacel.backend.domain.User;
import com.rozsacel.backend.domain.WorkPlace;
import com.rozsacel.backend.dto.AttendanceDto;
import com.rozsacel.backend.dto.WagePerLocationDto;
import com.rozsacel.backend.repository.AdvanceRepo;
import com.rozsacel.backend.repository.AttendanceRepo;
import com.rozsacel.backend.repository.UserRepo;
import com.rozsacel.backend.repository.WorkPlaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class BackendService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    AttendanceRepo attendanceRepo;

    @Autowired
    WorkPlaceRepo workPlaceRepo;

    @Autowired
    AdvanceRepo advanceRepo;

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
        if (user == null) {
            return null;
        }
        return user;
    }

    public List<AttendanceDto> getTimeSheet(int userId, int year, int month) {
        List<Attendance> attendances = attendanceRepo.getMonthlyTimeSheet(userId,year,month);
        List<AttendanceDto> dtos = new ArrayList<>();
        for (Attendance attendance : attendances) {
                AttendanceDto dto = new AttendanceDto();
                dto.index = attendance.getDate().getDayOfMonth() - 1;
                dto.from = attendance.getStartTime().toString().substring(0, 5);
                dto.to = attendance.getEndTime().toString().substring(0, 5);
                dto.place = attendance.getWorkPlace().getName();
                dto.placeId = attendance.getWorkPlace().getId();
                dtos.add(dto);
        }
        return dtos;
    }

    public List<WorkPlace> getAllWorkPlaces() {
        List<WorkPlace> workPlaces = workPlaceRepo.findAll();
        for (WorkPlace workPlace : workPlaces) {
            workPlace.setAttendance(null);
        }
        workPlaces.sort(Comparator.comparing(WorkPlace::getName));
        return workPlaces;
    }

    public void postAttendance(int userId, String from, String to, int placeId, int year, int month, int day) {

        User user = userRepo.findById(userId).get();
        WorkPlace workPlace = workPlaceRepo.findById(placeId).get();
        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setWorkPlace(workPlace);
        attendance.setStartTime(LocalTime.parse(from));
        attendance.setEndTime(LocalTime.parse(to));
        LocalDate date = LocalDate.of(year, month, day);
        attendance.setDate(date);

        attendanceRepo.save(attendance);

    }

    public void deleteAttendance(int userId, int year, int month, int day) {
        User user = userRepo.findById(userId).get();
        LocalDate date = LocalDate.of(year, month, day);

        Attendance attendance = attendanceRepo.findByUserAndDate(user, date);
        if(attendance!=null){
            attendanceRepo.delete(attendance);
        }
    }

    public String changePassword(int userId, String oldPass, String newPass) {
        User user = userRepo.findById(userId).get();
        if (user.getPassword().equals(oldPass)) {
            user.setPassword(newPass);
            userRepo.save(user);
        } else {
            return null;
        }
        return "OK";
    }

    public void updateWorkPlace(WorkPlace workPlace) {
        workPlaceRepo.save(workPlace);
    }

    public void newWorkPlace(String name, boolean isActive){
        WorkPlace workPlace = new WorkPlace();
        workPlace.setName(name);
        workPlace.setActive(isActive);
        workPlaceRepo.save(workPlace);
    }


    public List<WagePerLocationDto> getWageOfEmployee(int id, int year, int month) {
        List<Object> objectList =  attendanceRepo.getWagePerLocationList(id,year,month);
        List<WagePerLocationDto> dtos = new ArrayList<>();
        for (Object o : objectList) {
            if(o.getClass().isArray()) {
                Object[] objectArray = (Object[]) o;
                WagePerLocationDto dto = new WagePerLocationDto();
                dto.workPlace = objectArray[0].toString();
                dto.days = Integer.parseInt(objectArray[1].toString());
                dto.hours = Integer.parseInt(objectArray[2].toString());
                dto.weekendHours = Integer.parseInt(objectArray[3].toString());
                dto.minutes = Integer.parseInt(objectArray[4].toString());
                dto.weekendMinutes = Integer.parseInt(objectArray[5].toString());
                if(!dto.workPlace.equals("Hétvége") && !dto.workPlace.equals("Szabi") && !dto.workPlace.equals("Betegállomány")  && !dto.workPlace.equals("Ünnepnap")){
                    dtos.add(dto);
                }

            }
        }
        return dtos;
    }

    public void saveUser(User user) {
        User u = userRepo.findById(user.getId()).get();
        u.setName(user.getName());
        u.setUserName(user.getUserName());
        u.setBaseWage(user.getBaseWage());
        u.setWagePerHour(user.getWagePerHour());
        userRepo.save(u);
    }

    public void postAdvance(Advance advance){
        advanceRepo.save(advance);
    }
}
