package handlatter.config.filter;

import handlatter.config.jwt.JwtUtil;
import handlatter.config.oauth.dto.OAuth2UserPrincipal;
import handlatter.domain.entity.Role;
import handlatter.exception.AuthenticateException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthorizationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String accessToken = jwtUtil.resolveAccessToken(request);
        if (accessToken == null) {
            doFilter(request, response, filterChain);
            return;
        }

        log.info("액세스토큰 반환 완료: {}", accessToken);
        try {
            if (!jwtUtil.validateToken(accessToken)) {
                setAuthentication(accessToken);
            }
        } catch (AuthenticateException exception) {
            request.setAttribute("AuthenticateException", exception);
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String accessToken) {
        String oAuthName = jwtUtil.getOAuthName(accessToken);
        System.out.println(oAuthName);
        Authentication authentication = new UsernamePasswordAuthenticationToken(new OAuth2UserPrincipal(oAuthName, Map.of()), "",
                List.of(new SimpleGrantedAuthority(Role.USER.name())));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
