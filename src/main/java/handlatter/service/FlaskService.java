package handlatter.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@Service
@HttpExchange
public interface FlaskService {

    @PostExchange("/text")
    String getText(MultipartFile file);

}
