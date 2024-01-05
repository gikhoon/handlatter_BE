package handlatter.service;

import handlatter.domain.dto.LetterListResponse;
import handlatter.domain.dto.LetterResponse;
import handlatter.domain.dto.SaveLetterRequest;
import handlatter.domain.elastic.Letter;
import handlatter.domain.entity.Member;
import handlatter.exception.ApplicationException;
import handlatter.exception.ErrorCode;
import handlatter.global.s3.S3Uploader;
import handlatter.repository.LetterRepository;
import handlatter.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LetterService {

    private final S3Uploader s3Uploader;
    private final FlaskService flaskService;
    private final LetterRepository letterRepository;
    private final MemberRepository memberRepository;

    public String saveLetter(String oauthName, SaveLetterRequest request) {
        String imageUrl;
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            imageUrl = s3Uploader.uploadFiles(request.getImage(), "letter");
        } else {
            throw new ApplicationException(ErrorCode.DATE_INVALID);
        }
        log.info("imageUrl=" + imageUrl);

        String content = flaskService.getText(request.getImage());

        Member member = memberRepository.findByOauthName(oauthName)
                .orElseThrow(() -> new ApplicationException(ErrorCode.MEMBER_NOT_FOUND));

        Letter save = letterRepository.save(new Letter(member.getId(), content, imageUrl));

        return save.getId();
    }

    public LetterListResponse search(String oauthName) {
        Member member = memberRepository.findByOauthName(oauthName)
                .orElseThrow(() -> new ApplicationException(ErrorCode.MEMBER_NOT_FOUND));

        return LetterListResponse.from(letterRepository.findAllByMemberId(member.getId()).stream()
                .map(LetterResponse::from)
                .toList());

    }

    public LetterListResponse search(String oauthName, String keyword) {
        Member member = memberRepository.findByOauthName(oauthName)
                .orElseThrow(() -> new ApplicationException(ErrorCode.MEMBER_NOT_FOUND));

        return LetterListResponse.from(letterRepository.searchByKeywordAndMemberId(keyword, member.getId()).stream()
                .map(LetterResponse::from)
                .toList());
    }
}
