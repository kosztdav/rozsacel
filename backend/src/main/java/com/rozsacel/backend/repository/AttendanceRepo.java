package com.rozsacel.backend.repository;

import com.rozsacel.backend.domain.Attendance;
import com.rozsacel.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Integer> {

    @Query
    List<Attendance> findAllByUser(User user);

    @Query
    Attendance findByUserAndDate(User user, LocalDate date);
}
