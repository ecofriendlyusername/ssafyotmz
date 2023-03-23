package com.patandmat.otmz.domain.look.api;

import com.patandmat.otmz.domain.look.api.model.LookResponseDto;
import com.patandmat.otmz.domain.look.application.LookService;
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
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class LookController {
    private final String SUCCESS = "SUCCESS";
    private final String FAIL = "FAIL";
    private final LookService lookService;

    @PostMapping("/look")
    public ResponseEntity<?> saveLook(@RequestPart("imagefile") MultipartFile file, @RequestBody String style, Authentication authentication) throws IOException {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();

        try {
            lookService.saveLook(file, style, member.getId());
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("User Does Not Exist", HttpStatus.BAD_REQUEST);
        } catch (NoSuchMemberException e) {
            return new ResponseEntity<>("User Doesn't Exist", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().build();
    }


    @GetMapping("/looks")
    public ResponseEntity<?> getLookPage(Pageable pageable, Authentication authentication) throws AttributeNotFoundException, NoSuchMemberException {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();

        try {
            Page<LookResponseDto> page = lookService.getLooks(pageable, member.getId());

            return new ResponseEntity<>(page, HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (AttributeNotFoundException e) {

            return new ResponseEntity<>("Wrong Category Value", HttpStatus.BAD_REQUEST);
        } catch (NoSuchMemberException e) {

            return new ResponseEntity<>("User Doesn't Exist", HttpStatus.BAD_REQUEST);
        }
    }
}
