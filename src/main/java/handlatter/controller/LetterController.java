package handlatter.controller;

import handlatter.config.oauth.dto.OAuth2UserPrincipal;
import handlatter.domain.dto.LetterListResponse;
import handlatter.domain.dto.SaveLetterRequest;
import handlatter.service.LetterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/letter")
@RequiredArgsConstructor
@Slf4j
public class LetterController {

    private final LetterService letterService;

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> savePost(
            @AuthenticationPrincipal OAuth2UserPrincipal principal,
            @ModelAttribute SaveLetterRequest request) {
        log.info("serviceStart");
        letterService.saveLetter(principal.getName(), request);
        log.info("serviceEnd");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public LetterListResponse search(
            @RequestParam(required = false) String keyword,
            @AuthenticationPrincipal OAuth2UserPrincipal principal
    ) {
        if (keyword == null) {
            return letterService.search(principal.getName());
        }
        return letterService.search(principal.getName(), keyword);
    }
}
