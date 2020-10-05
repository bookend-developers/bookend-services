package com.bookclupservice.bookclubservice.repository;

import com.bookclupservice.bookclubservice.model.SharePostRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SharePostRequestRepository extends JpaRepository<SharePostRequest,Long> {

    List<SharePostRequest> findByClubId(Long id);
}
