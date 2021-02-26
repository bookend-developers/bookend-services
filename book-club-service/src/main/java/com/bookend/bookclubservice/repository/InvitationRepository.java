package com.bookend.bookclubservice.repository;

import com.bookend.bookclubservice.model.Club;
import com.bookend.bookclubservice.model.Invitation;
import com.bookend.bookclubservice.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation,Long> {

    List<Invitation> findByClubId(Long clubId);
    List<Invitation> findInvitationsByInvitedPerson(Member username);
    Invitation findByClubAndInvitedPerson(Club club, Member member);

}
