package com.bookclupservice.bookclubservice.repository;

import com.bookclupservice.bookclubservice.model.ClubMemberRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubMemberRequestRepository extends JpaRepository<ClubMemberRequest, Long> {
}
