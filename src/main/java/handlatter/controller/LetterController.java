package handlatter.controller;

import handlatter.domain.dto.SaveLetterRequest;
import handlatter.exception.ImageException;
import handlatter.service.LetterService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/letter")
@RequiredArgsConstructor
@Slf4j
public class LetterController {

    private final LetterService letterService;

    @PostMapping("/save")
    public ResponseEntity<?> savePost(@RequestBody SaveLetterRequest request) throws ImageException {
        log.info("serviceStart");
        letterService.saveLetter(request);
        log.info("serviceEnd");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public String test() {
        return "1";
    }
}
