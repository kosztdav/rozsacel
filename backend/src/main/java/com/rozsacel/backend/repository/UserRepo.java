package com.rozsacel.backend.repository;

import com.rozsacel.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {

    @Query
    User findByUserName(String userName);

    @Query
    User findByUserNameAndPassword(String userName, String password);
}
