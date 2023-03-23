package com.patandmat.otmz.domain.member.application;


import com.patandmat.otmz.domain.item.repository.ItemRepository;
import com.patandmat.otmz.domain.look.api.model.LookCountDto;
import com.patandmat.otmz.domain.look.repository.LookRepository;
import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final LookRepository lookRepository;
    private final ItemRepository itemRepository;

    public Member getMemberWithAuthId(Long authId) {
        Member member = memberRepository.findByAuthId(authId);
        if (member == null || member.isDeleted()) {
            return null;
        }
        return member;
    }

    public void joinWithAuthId(Long authId, String nickname, String profileImg) {
        Member member = memberRepository.findByAuthId(authId);
        if (member == null) {
            Member newMember = new Member();
            newMember.setAuthId(authId);
            newMember.setProfileImagePath(profileImg);
            newMember.setNickname(nickname);
            memberRepository.save(newMember);
            System.out.println("회원가입 성공");
        } else {
            member.restore();
            memberRepository.save(member);
            System.out.println("회원가입 성공[재가입]");
        }
    }

    public String getRefreshToken(long id) {
        Member user = memberRepository.findById(id).orElseThrow(NoSuchElementException::new);
        if (user != null) return user.getRefreshToken();

        return null;
    }

    @Transactional
    public void deleteMember(Long id) {
        Optional<Member> userOptional = memberRepository.findById(id);
        if (userOptional.isPresent()) {
            Member user = userOptional.get();
            user.delete();
            memberRepository.save(user);
        }
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(NoSuchElementException::new);
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

    public int getTotalStyleCount(long memberId) {
        int totalStyleCount = lookRepository.countByMemberId(memberId);

        return totalStyleCount;
    }

    public int getTotalItemCount(Long memberId) {
        int totalItemCount = itemRepository.countByMemberId(memberId);

        return totalItemCount;
    }

    public List<LookCountDto> getStyleSummary(Long memberId) {
        List<LookCountDto> list = lookRepository.findByMemberIdOrderByStyleDesc(memberId);

        return list;
    }
}
