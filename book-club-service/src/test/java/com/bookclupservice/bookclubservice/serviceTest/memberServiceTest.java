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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
        given(memberRepository.save(any(Member.class))).willReturn(member);
        final Member expected = memberService.save(member.getId(),member.getUserName());
        assertThat(expected).isNotNull();
        assertEquals(expected,member);
    }
    @Test
    void shouldNotSaveWhenIdOrUsernameNull(){
        Member member = new Member();
        member.setId(null);
        member.setUserName("testdeneme");
        Member member2 = new Member();
        member2.setId((long)1);
        member2.setUserName(null);
        assertThrows(IllegalArgumentException.class,()->{
            memberService.save(member.getId(),member.getUserName());
        });
        assertThrows(IllegalArgumentException.class,()->{
            memberService.save(member2.getId(),member2.getUserName());
        });
        verify(memberRepository, never()).save(any(Member.class));
    }
}
