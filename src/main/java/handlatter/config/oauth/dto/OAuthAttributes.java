package handlatter.config.oauth.dto;


import handlatter.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Getter
@Builder
@Slf4j
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String oauthName;

    public static OAuthAttributes of(String registrationId, Map<String, Object> attributes){
        if ("kakao".equals(registrationId)) {
            return ofKakao("id", attributes);
        }
        return null;
    }

    private static OAuthAttributes ofKakao(String memberNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
        Map<String ,Object> account = (Map<String, Object>) attributes.get("profile");

        log.info(response.toString());
        log.info("account= :" +account.toString());


        return null;
    }

    public Member toEntity(){
        return Member.builder()
                .build();
    }
}
