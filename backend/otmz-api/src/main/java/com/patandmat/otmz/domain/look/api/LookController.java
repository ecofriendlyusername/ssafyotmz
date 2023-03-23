package com.patandmat.otmz.domain.look.api;

import com.patandmat.otmz.domain.item.exception.NoSuchMemberException;
import com.patandmat.otmz.domain.look.api.model.RecommendedLookResponse;
import com.patandmat.otmz.domain.look.application.LookRecommendService;
import com.patandmat.otmz.domain.look.application.LookService;
import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.global.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
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
public class LookController {
    private final LookService lookService;

    private final LookRecommendService lookRecommendService;

    @PostMapping("/look")
    public ResponseEntity<?> saveLook(@RequestPart("imagefile") MultipartFile file, @RequestBody String style, Authentication authentication) throws IOException {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();

        try {
            lookService.saveLook(file, style, member.getId());
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("User Does Not Exist", HttpStatus.BAD_REQUEST);
        } catch (AttributeNotFoundException e) {
            return new ResponseEntity<>("Check Attributes of The Item", HttpStatus.BAD_REQUEST);
        } catch (NoSuchMemberException e) {
            return new ResponseEntity<>("User Doesn't Exist", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/look/recommended")
    public ResponseEntity<List<RecommendedLookResponse>> recommendLooks(Authentication authentication,
                                                                        @RequestParam(required = false, defaultValue = "3") Integer size,
                                                                        @RequestParam(required = false, defaultValue = "false") boolean reversed) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();

        List<RecommendedLookResponse> looks = lookRecommendService.getRecommendedLooks(member, size, reversed);

        return ResponseEntity.ok(looks);
    }
}
