package com.rozsacel.backend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attendance_sheet")
@NoArgsConstructor
@ToString
@Getter
@Setter
@IdClass(AttendanceID.class)
//@NamedStoredProcedureQuery(name = "Attendance.getWagePerLocationList",
//        procedureName = "getWagePerLocationList", parameters = {
//        @StoredProcedureParameter(mode = ParameterMode.IN, name = "user_id", type = Integer.class),
//        @StoredProcedureParameter(mode = ParameterMode.IN, name = "year", type = Integer.class),
//        @StoredProcedureParameter(mode = ParameterMode.IN, name = "month", type = Integer.class)
//       })
public class Attendance {

    @Id
    @ManyToOne
    User user;

    @Id
    LocalDate date;

    LocalTime startTime;
    LocalTime endTime;

    @ManyToOne
    WorkPlace workPlace;
}

