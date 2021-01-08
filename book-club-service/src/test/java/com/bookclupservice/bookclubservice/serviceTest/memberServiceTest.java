package com.bookclupservice.bookclubservice.serviceTest;

import com.bookclupservice.bookclubservice.model.Member;
import com.bookclupservice.bookclubservice.repository.MemberRepository;
import com.bookclupservice.bookclubservice.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class memberServiceTest {

    @Mock
    MemberRepository memberRepository;
    @InjectMocks
    MemberService memberService;

    @Test
    void shouldSaveMember(){
        Member member = new Member();
        member.setId((long)1);
        member.setUserName("testdeneme");
        given(memberRepository.save(member)).willReturn(member);
        final Member expected = memberService.save(member.getId(),member.getUserName());
        assertThat(expected).isNotNull();
        assertEquals(expected,member);
    }
}
