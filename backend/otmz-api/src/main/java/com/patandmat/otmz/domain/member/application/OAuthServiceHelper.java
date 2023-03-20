package com.patandmat.otmz.domain.member.application;

import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OAuthServiceHelper {
    // get id token and save refresh token
    MemberRepository memberRepository;
    @Autowired
    public OAuthServiceHelper(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public void saveRefreshToken(Long authId, String refreshToken) { //
        // check if there's a user with the sub
        Member member = memberRepository.findByAuthId(authId);

        if (member == null) {
            Member newMember = new Member();
            newMember.setAuthId(authId);
            newMember.setRefreshToken(refreshToken);
            memberRepository.save(newMember);
        } else {
            member.setRefreshToken(refreshToken);
            memberRepository.save(member);
        }
    }
}
