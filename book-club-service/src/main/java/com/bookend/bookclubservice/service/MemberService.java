package com.bookend.bookclubservice.service;

import com.bookend.bookclubservice.model.Member;
import com.bookend.bookclubservice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    public Member save(Long id, String userName){
        if(id==null || userName==null)
            throw new IllegalArgumentException("id and username must not be null");
        Member member = new Member();
        member.setId(id);
        member.setUserName(userName);
        return memberRepository.save(member);
    }

}
