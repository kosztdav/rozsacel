package com.rozsacel.backend.repository;

import com.rozsacel.backend.domain.WorkPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkPlaceRepo extends JpaRepository<WorkPlace, Integer> {
}
