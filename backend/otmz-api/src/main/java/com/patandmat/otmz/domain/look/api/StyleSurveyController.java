package com.patandmat.otmz.domain.look.api;

import com.patandmat.otmz.domain.look.api.model.SurveyStyleResponse;
import com.patandmat.otmz.domain.look.application.LookService;
import com.patandmat.otmz.domain.look.entity.Style;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class StyleSurveyController {

    private final LookService lookService;

    @GetMapping("")
    public ResponseEntity<List<SurveyStyleResponse>> getStylesForSurvey() {
        List<SurveyStyleResponse> surveyStyles = new ArrayList<>();
        for (Style style : Style.values()) {
            surveyStyles.addAll(lookService.getLooksByStyle(style, 2));
        }

        return ResponseEntity.ok(surveyStyles);
    }
}
