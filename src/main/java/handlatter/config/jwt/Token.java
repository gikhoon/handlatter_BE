package handlatter.config.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Token {

    private String accessToken;

    @Builder
    public Token(String accessToken){
        this.accessToken = accessToken;
    }
}
