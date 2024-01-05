package handlatter.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthenticateException extends AuthenticationException {
    public AuthenticateException(String msg) {
        super(msg);
    }
}
