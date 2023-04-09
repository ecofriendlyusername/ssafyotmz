package com.patandmat.otmz.domain.look.api;

import com.patandmat.otmz.domain.look.api.model.SurveyStyleRequest;
import com.patandmat.otmz.domain.look.api.model.SurveyStyleResponse;
import com.patandmat.otmz.domain.look.application.LookService;
import com.patandmat.otmz.domain.look.entity.Style;
import com.patandmat.otmz.domain.member.application.MemberService;
import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.global.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class StyleSurveyController {

    private final LookService lookService;

    private final MemberService memberService;

    @GetMapping("")
    public ResponseEntity<List<SurveyStyleResponse>> getStylesForSurvey() {
        List<SurveyStyleResponse> surveyStyles = new ArrayList<>();
        for (Style style : Style.values()) {
            surveyStyles.addAll(lookService.getLooksByStyle(style));
        }


        return ResponseEntity.ok(surveyStyles);
    }

    @PostMapping("")
    public ResponseEntity<Boolean> initStyleStat(@RequestBody List<SurveyStyleRequest> surveyStyleRequests, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();

        try {
            memberService.setInitialStyleStats(member, surveyStyleRequests);
        } catch (Exception e) {
            log.info("error: {}", e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(true);
    }
}
