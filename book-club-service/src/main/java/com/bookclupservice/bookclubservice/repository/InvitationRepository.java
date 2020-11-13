package com.bookclupservice.bookclubservice.repository;

import com.bookclupservice.bookclubservice.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation,Long> {

    List<Invitation> findByClubId(Long clubId);
    List<Invitation> findByInvitedPersonId(Long personId);

}
