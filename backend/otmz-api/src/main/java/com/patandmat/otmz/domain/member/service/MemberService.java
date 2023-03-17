package com.patandmat.otmz.domain.member.service;


import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class MemberService {
    private MemberRepository memberRepository;



    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;

    }



    @Transactional
    public void logout(long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setRefreshToken(null);
            memberRepository.save(member);
        }
    }

    public Member getMemberWithAuthId(Long authId) {
        Member member = memberRepository.findByAuthId(authId);
        if (member == null || member.getIsDeleted() == 1) return null;
        return member;
    }

    public void joinWithAuthId(Long authId, String nickname, String profileImg) {
        Member member = memberRepository.findByAuthId(authId);
        if (member == null) {
            Member newMember = new Member();
            newMember.setAuthId(authId);
            newMember.setProfileImg(profileImg);
            newMember.setNickname(nickname);
            memberRepository.save(newMember);
            System.out.println("회원가입 성공");
        } else {
            member.setIsDeleted(0);
            memberRepository.save(member);
            System.out.println("회원가입 성공[재가입]");
        }
    }
    public String getRefreshToken(long id) {
        Member user = memberRepository.getById(id);
        if (user != null) return user.getRefreshToken();
        return null;
    }
    @Transactional
    public void userDelete(Long id) {
        Optional<Member> userOptional = memberRepository.findById(id);
        if (userOptional.isPresent()) {
            Member user = userOptional.get();
            user.setIsDeleted(1);
            memberRepository.save(user);
        }
    }



    public Member getUserInfoById(Long id) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            Member memberToReturn = new Member();
            memberToReturn.setId(member.getId());
            memberToReturn.setNickname(member.getNickname());
            memberToReturn.setJoinDate(member.getJoinDate());
            return memberToReturn;
        } else return null;
    }




}
