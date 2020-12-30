package com.rozsacel.backend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cash_advances")
@NoArgsConstructor
@ToString
@Getter
@Setter

public class Advance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    Integer id;

    @ManyToOne
    User user;

    LocalDate date;
    Integer amount;

    @ManyToOne
    Type type;
}
