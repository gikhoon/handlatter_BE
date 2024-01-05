package handlatter.domain.dto;

import java.util.List;

public record LetterListResponse(
        List<LetterResponse> letterResponseList
) {
    public static LetterListResponse from(List<LetterResponse> list) {
        return new LetterListResponse(list);
    }
}
