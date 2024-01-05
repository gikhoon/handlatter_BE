package handlatter.domain.dto;

import handlatter.domain.elastic.Letter;

public record LetterResponse(
        String id,
        Long memberId,
        String content,
        String url
) {

    public static LetterResponse from(Letter entity) {
        return new LetterResponse(entity.getId(), entity.getMemberId(), entity.getContent(), entity.getUrl());
    }
}
