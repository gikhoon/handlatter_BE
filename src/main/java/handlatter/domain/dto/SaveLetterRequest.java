package handlatter.domain.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class SaveLetterRequest {
    MultipartFile image;
}
