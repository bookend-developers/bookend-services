package com.bookclupservice.bookclubservice.repository;

import com.bookclupservice.bookclubservice.model.ClubMemberRequest;
import com.bookclupservice.bookclubservice.model.SharePostRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubMemberRequestRepository extends JpaRepository<ClubMemberRequest, Long> {

    List<ClubMemberRequest> findByClubId(Long id);

}
