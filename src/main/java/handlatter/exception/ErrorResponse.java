package handlatter.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale = "Asia/Seoul")
    private final LocalDateTime time;
    private final HttpStatus status;
    private final String message;
    private final String requestURI;

    public ErrorResponse(LocalDateTime time, HttpStatus status, String message, String requestURI) {
        this.time = time;
        this.status = status;
        this.message = message;
        this.requestURI = requestURI;
    }
}
