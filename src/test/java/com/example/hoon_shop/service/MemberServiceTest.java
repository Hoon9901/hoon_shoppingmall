package com.example.hoon_shop.service;

import com.example.hoon_shop.dto.MemberFormDto;
import com.example.hoon_shop.entity.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional  // 쿼리 반영안하기
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setName("박성훈");
        memberFormDto.setEmail("test@gmail.com");
        memberFormDto.setAddress("김해");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    public void 회원가입_테스트() {
        Member member = createMember();
        Member savedMember = memberService.saveMember(member);

        assertThat(member.getName()).isEqualTo(savedMember.getName());
        assertThat(member.getEmail()).isEqualTo(savedMember.getEmail());
        assertThat(member.getAddress()).isEqualTo(savedMember.getAddress());
        assertThat(member.getPassword()).isEqualTo(savedMember.getPassword());
        assertThat(member.getRole()).isEqualTo(savedMember.getRole());
    }

    @Test
    public void 중복_회원가입_테스트() {
        Member member1 = createMember();
        Member member2 = createMember();
        memberService.saveMember(member1);
        // 예외 처리 테스트
        Throwable throwable = assertThrows(IllegalStateException.class, () -> {
            memberService.saveMember(member2);
        });

        assertThat("이미 가입된 회원입니다.").isEqualTo(throwable.getMessage());
    }
}