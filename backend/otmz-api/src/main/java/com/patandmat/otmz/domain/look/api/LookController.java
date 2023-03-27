package com.patandmat.otmz.domain.look.api;

import com.patandmat.otmz.domain.look.api.model.LookListDto;
import com.patandmat.otmz.domain.look.api.model.LookResponseDto;
import com.patandmat.otmz.domain.look.api.model.LookSaveResponse;
import com.patandmat.otmz.domain.look.api.model.RecommendedLookResponse;
import com.patandmat.otmz.domain.look.application.LookRecommendService;
import com.patandmat.otmz.domain.look.application.LookService;
import com.patandmat.otmz.domain.look.entity.Look;
import com.patandmat.otmz.domain.member.application.MemberService;
import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.global.auth.CustomUserDetails;
import com.patandmat.otmz.global.exception.NoSuchMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.AttributeNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/looks")
public class LookController {
    private final LookService lookService;
    private final MemberService memberService;

    private final LookRecommendService lookRecommendService;

    @PostMapping("")
    public ResponseEntity<?> saveLook(@RequestPart("imageFile") MultipartFile file, @RequestPart("styleVector") String styleVector, Authentication authentication) throws IOException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();
        Look look;

        try {
            look = lookService.saveLook(file, styleVector, member);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("User Does Not Exist", HttpStatus.BAD_REQUEST);
        } catch (NoSuchMemberException e) {
            return new ResponseEntity<>("User Doesn't Exist", HttpStatus.BAD_REQUEST);
        }

        memberService.updateStyleStat(member, styleVector);

        return ResponseEntity.ok().body(new LookSaveResponse(look.getId(), look.getImage().getId()));
    }

    @GetMapping("/recommended")
    public ResponseEntity<List<RecommendedLookResponse>> recommendLooks(Authentication authentication,
                                                                        @RequestParam(required = false, defaultValue = "3") Integer size,
                                                                        @RequestParam(required = false, defaultValue = "false") boolean reversed) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();

        List<RecommendedLookResponse> looks = lookRecommendService.getRecommendedLooks(member, size, reversed);

        return ResponseEntity.ok(looks);
    }

    @GetMapping("")
    public ResponseEntity<?> getLookPage(Pageable pageable, Authentication authentication) throws AttributeNotFoundException, NoSuchMemberException {

        LookListDto result = new LookListDto();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();
        int totalStyle = lookService.getCountOfLooks(member.getId());
        String nickname = member.getNickname();

        result.setTotalStyle(totalStyle);
        result.setNickname(nickname);

        try {
            Page<LookResponseDto> page = lookService.getLooks(pageable, member.getId());
            result.setPage(page);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (AttributeNotFoundException e) {

            return new ResponseEntity<>("Wrong Style Value", HttpStatus.BAD_REQUEST);
        } catch (NoSuchMemberException e) {

            return new ResponseEntity<>("User Doesn't Exist", HttpStatus.BAD_REQUEST);
        }
    }
}
