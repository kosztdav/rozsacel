package com.rozsacel.backend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "work_places")
@NoArgsConstructor
@ToString
@Getter
@Setter
public class WorkPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    int id;

    String name;

    boolean isActive;

    @OneToMany(mappedBy = "workPlace")
    List<Attendance> attendance;

}
