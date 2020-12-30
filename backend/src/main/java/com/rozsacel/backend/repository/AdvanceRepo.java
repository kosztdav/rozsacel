package com.rozsacel.backend.repository;

import com.rozsacel.backend.domain.Advance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AdvanceRepo extends JpaRepository<Advance, Integer> {

}