package com.rozsacel.backend.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class AttendanceID implements Serializable {

    int user;
    LocalDate date;


}
