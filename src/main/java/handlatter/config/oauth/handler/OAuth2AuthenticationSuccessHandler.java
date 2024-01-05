package handlatter.config.oauth.handler;

import handlatter.config.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("oAuth2AuthenticationSuccessHandler");
        log.info(authentication.getPrincipal().toString());

        OAuth2User principal = (OAuth2User) authentication.getPrincipal();

        String token = jwtUtil.createAccessToken(principal.getName());

        log.info("token.toString :" + token);
        String redirectUrl = "http://localhost:8080?accessToken=" + token;

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);

        log.info(principal.getName());
    }
}
