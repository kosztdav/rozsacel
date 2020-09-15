package com.rozsacel.backend.repository;

import com.rozsacel.backend.domain.Attendance;
import com.rozsacel.backend.domain.User;
import com.rozsacel.backend.dto.AttendanceDto;
import com.rozsacel.backend.dto.WagePerLocationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Integer> {

    @Query
    List<Attendance> findAllByUser(User user);

    @Query
    Attendance findByUserAndDate(User user, LocalDate date);

    @Query("SELECT a FROM Attendance a WHERE a.user.id = ?1 and year(a.date) = ?2 and month(a.date) = ?3")
    List<Attendance> getMonthlyTimeSheet(int userId, int year, int month);

    @Query(value = "SELECT * from dbo.getWagePerLocationList(?1,?2,?3)", nativeQuery = true)
    List<Object> getWagePerLocationList(int user_id, int year, int month);

}
