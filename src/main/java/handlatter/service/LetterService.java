package handlatter.service;

import handlatter.domain.dto.SaveLetterRequest;
import handlatter.exception.ErrorCode;
import handlatter.exception.ImageException;
import handlatter.global.s3.S3Uploader;
import handlatter.repository.LetterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class LetterService {

    private final S3Uploader s3Uploader;
    private final LetterRepository letterRepository;
    public void saveLetter(SaveLetterRequest request) throws ImageException {
        String imageUrl = null;
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            imageUrl = s3Uploader.uploadFiles(request.getImage(), "letter");
        }else{
            throw new ImageException(ErrorCode.DATE_INVALID);
        }
        log.info("imageUrl=" + imageUrl);
        //TODO 파이썬 타고 가기
    }

}
