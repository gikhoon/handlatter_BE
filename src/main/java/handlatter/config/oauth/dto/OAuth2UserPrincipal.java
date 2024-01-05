package handlatter.config.oauth.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class OAuth2UserPrincipal implements OAuth2User {
    private String oauthName;
    private Map<String, Object> attributes;

    public OAuth2UserPrincipal(String oauthName, Map<String, Object> attributes) {
        this.oauthName = oauthName;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return oauthName;
    }
}
