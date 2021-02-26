package com.bookend.bookclubservice.repository;

import com.bookend.bookclubservice.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Member findByUserName(String userName);
}
