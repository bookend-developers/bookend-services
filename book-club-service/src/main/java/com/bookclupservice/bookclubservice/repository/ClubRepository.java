package com.bookclupservice.bookclubservice.repository;

import com.bookclupservice.bookclubservice.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club,Long> {
}
