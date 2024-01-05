package handlatter.config.oauth.utils;

import handlatter.domain.entity.Member;
import handlatter.domain.entity.Role;
import handlatter.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuth2MemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    public CustomOAuth2MemberService(MemberRepository memberRepository, HttpSession httpSession){
        this.memberRepository = memberRepository;
        this.httpSession = httpSession;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String oauthName = registrationId + "_" + oAuth2User.getAttributes().get("id").toString();
        Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String nickName = (String) profile.get("nickname");
        saveOrUpdate(oauthName, nickName);

        return oAuth2User;
    }

    private Member saveOrUpdate(String oauthName, String nickName){
        return memberRepository.findByOauthName(oauthName)
                .orElse(memberRepository.save(Member.builder().nickName(nickName).oauthName(oauthName).role(Role.USER).build()));
    }
}
