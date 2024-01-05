//package handlatter.service;
//
//import handlatter.config.jwt.JwtTokenProvider;
//import handlatter.domain.entity.Member;
//import handlatter.repository.MemberRepository;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
//@Service
//public class MemberService {
//
//    private final BCryptPasswordEncoder encoder;
//    private final MemberRepository repository;
//    private final AuthenticationManagerBuilder authenticationManagerBuilder;
//    private final JwtTokenProvider jwtTokenProvider;
//
//    public MemberService(BCryptPasswordEncoder encoder, MemberRepository repository, AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider jwtTokenProvider) {
//        this.encoder = encoder;
//        this.repository = repository;
//        this.authenticationManagerBuilder = authenticationManagerBuilder;
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//
//    public String login(String email, String password) {
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
//
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        String token = jwtTokenProvider.generateToken(authentication);
//
//        return token;
//    }
//
////    public Long signup(SignupForm signupForm) {
////        boolean check = checkEmailExists(signupForm.getEmail());
////
////        if (check) {
////            throw new IllegalArgumentException("이미 존재하는 유저입니다.");
////        }
////
////        String encPwd = encoder.encode(signupForm.getPassword());
////
////        Member user = repository.save(signupForm.toEntity(encPwd));
////
////        if(user!=null) {
////            return user.getId();
////        }
////        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
////    }
////
////    public boolean checkEmailExists(String email) {
////        return repository.existsUsersByEmail(email);
////    }
//}
