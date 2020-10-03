package com.bookend.authorizationserver.repository;

import com.bookend.authorizationserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<User,Integer> {


    Optional<User> findByUsername(String name);
    Optional<User> findByEmail(String email);

}
