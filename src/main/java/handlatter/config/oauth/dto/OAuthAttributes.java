package handlatter.config.oauth.dto;


import handlatter.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

import static com.amazonaws.services.ec2.model.PrincipalType.Role;

@Getter
@Builder
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    public static OAuthAttributes of(String registraionId, Map<String, Object> attributes){
        if ("kakao".equals(registraionId)) {
            return ofKakao("id", attributes);
        }
    }

    private static OAuthAttributes ofKakao(String memberNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
        Map<String ,Object> account = (Map<String, Object>) attributes.get("profile");

        return OAuthAttributes.builder()
                .name((String) account.get("name"))
                .email((String) response.get("email"))
                .attributes(response)
                .nameAttributeKey(memberNameAttributeName)
                .build();
    }

    public Member toEntity(){
        return Member.builder()
                .name(name)
                .email(email)
                .build();
    }
}
