package com.bookclupservice.bookclubservice.service;

import com.bookclupservice.bookclubservice.model.Member;
import com.bookclupservice.bookclubservice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    public Member save(Long id, String userName){
        Member member = new Member();
        member.setId(id);
        member.setUserName(userName);
        return memberRepository.save(member);
    }
    public Member find(String username){
        return memberRepository.findByUserName(username);
    }
}
