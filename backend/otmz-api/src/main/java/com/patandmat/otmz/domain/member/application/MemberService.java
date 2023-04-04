package com.patandmat.otmz.domain.member.application;


import com.patandmat.otmz.domain.item.repository.ItemRepository;
import com.patandmat.otmz.domain.look.api.model.LookResponse;
import com.patandmat.otmz.domain.look.api.model.StyleByCountResponse;
import com.patandmat.otmz.domain.look.entity.Style;
import com.patandmat.otmz.domain.look.repository.LookRepository;
import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.domain.member.repository.MemberRepository;
import com.patandmat.otmz.global.utils.VectorParser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final LookRepository lookRepository;
    private final ItemRepository itemRepository;

    private final int NUM_OF_CATEGORY = 3;

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

    public List<StyleByCountResponse> getStyleSummary(Long memberId) {
        return lookRepository.findByMemberIdOrderByStyleDesc(memberId);
    }

    public void updateStyleStat(Member member, String styleVector) {
        Map<String, Double> styleMap = VectorParser.parseToMap(styleVector, VectorParser.STYLE_KEY, VectorParser.STYLE_VALUE);

        Map<String, Double> memberStyleStatMap;

        if (member.getLookStyleStat() != null) {
            memberStyleStatMap = VectorParser.parseToMap(member.getLookStyleStat());
            memberStyleStatMap.replaceAll((style, score) -> (memberStyleStatMap.get(style) + styleMap.getOrDefault(style, 0d)) / 2);
        } else {
            memberStyleStatMap = styleMap;
        }

        member.setLookStyleStat(VectorParser.parseToString(memberStyleStatMap));
        memberRepository.save(member);
    }

    public void updateItemStyleStat(Member member, String styleVector) {
        System.out.println("styleVector : " + styleVector);
        Map<String, Double> styleMap = VectorParser.parseToMap(styleVector, VectorParser.STYLE_KEY, VectorParser.STYLE_VALUE);

        Map<String, Double> memberStyleStatMap;

        if (member.getLookStyleStat() != null) {
            memberStyleStatMap = VectorParser.parseToMap(member.getItemStyleStat());
            memberStyleStatMap.replaceAll((style, score) -> (memberStyleStatMap.get(style) + styleMap.getOrDefault(style, 0d)) / 2);
        } else {
            memberStyleStatMap = styleMap;
        }

        member.setItemStyleStat(VectorParser.parseToString(memberStyleStatMap));
        memberRepository.save(member);
    }

    public Map<String, List<LookResponse>> getStyles(Long memberId, int size) {
        Map<String, List<LookResponse>> result = new HashMap<>();

        List<StyleByCountResponse> styleByCountResponses = lookRepository.findByMemberIdOrderByStyleDesc(memberId);
        for (int i = 0; i < Math.min(NUM_OF_CATEGORY, styleByCountResponses.size()); i++) {
            Style style = styleByCountResponses.get(i)
                                               .getStyle();

            result.put(style.getKey(), lookRepository.findAllByStyleOrderByCreatedAtDesc(style, PageRequest.of(0, size))
                                                     .stream()
                                                     .parallel()
                                                     .map(look ->
                                                             LookResponse.builder()
                                                                         .id(look.getId())
                                                                         .imageId(look.getImage().getId())
                                                                         .memberId(look.getMember().getId())
                                                                         .ownerName(look.getMember().getNickname())
                                                                         .style(look.getStyle().getKey())
                                                                         .build())
                                                     .collect(Collectors.toList()));
        }

        return result;
    }
}
