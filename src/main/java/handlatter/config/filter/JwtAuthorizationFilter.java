package handlatter.config.filter;

import handlatter.config.jwt.JwtUtil;
import handlatter.domain.entity.Role;
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
        if (!jwtUtil.validateToken(accessToken)) {
            setAuthentication(accessToken);
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String accessToken) {
        log.info("setAuthentication");
        String email = jwtUtil.getOAuthName(accessToken);
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(email, "",
                List.of(new SimpleGrantedAuthority(Role.USER.name())));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

}
