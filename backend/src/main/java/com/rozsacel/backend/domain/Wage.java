package com.rozsacel.backend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "wage_session")
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Wage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    int id;

    int basic;
    int daily;

    @OneToOne
    User user;



}
