package com.bookclupservice.bookclubservice.repository;

import com.bookclupservice.bookclubservice.model.Club;
import com.bookclupservice.bookclubservice.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<Club,Long> {

    List<Club> findByOwner(Member owner);


}
