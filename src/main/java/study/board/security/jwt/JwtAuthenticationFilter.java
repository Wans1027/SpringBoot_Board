package study.board.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import study.board.entity.Member;
import study.board.security.SecretKey;
import study.board.security.auth.PrincipalDetails;


import java.io.IOException;
import java.util.Date;

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음.
// 언제 동작하냐면 /login 요청해서 username, password 전송하면 (post)
// UsernamePasswordAuthenticationFilter 가 동작을 함.
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    //login 요청을 하면 로그인 시도를 위해서 실행되는 함수.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("JwtAuthenticationFilter: 로그인 시도 중 ++++++++++++++");
        /**
         * 1. username, password 를 받아서
         * 2. 정상인지 로그인 시도를 해본다. authenticationManager 로 로그인 시도를 하면
         * 3. PrincipalDetailsService 가 호출
         * 4. PrincipalDetailsService 의 loadUserByUsername() 이 자동으로 실행됨.
         * 5. PrincipalDetails 에 세션을 담고 (권한 관리를 위해)
         * 6. JWT 토큰을 만들어서 응답해주면 됨
         */
        ObjectMapper om = new ObjectMapper();
        try {
            Member member = om.readValue(request.getInputStream(), Member.class); // /login 으로 들어온 json 파일을 객체로 변환
            log.info("{}", member);

            //토큰만들기
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(member.getUsername(),member.getPassword());
            //PrincipalDetailsService 의 loadUserByUsername() 함수가 실행된 후 정상이면 authentication 이 생성됨
            //정상이라는 것은 DB에 있는 username 과 password 가 일치한다.
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 확인용.
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            log.info("로그인 완료!!= username: {},userId: {}", principalDetails.getMember().getUsername(), principalDetails.getMember().getId());//로그인 정상적으로 되었다는 뜻
            //authentication 객체가 session 영역에 저장을 해야하고 그 방법이 return 해주면 됨.
            //리턴의 이유는 권한 관리를 security 가 대신 해주기 때문에 편하려고
            // 굳이 JWT 토큰을 사용하면서 세션을 만들 이유가 없음. 근데 단지 권한 처리때문에 Session 을 넣어 줍니다.

            return authentication;
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }
    //attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행.
    // JWT 토큰을 만들어서 request 요청 한 사용자에게 JWT 토큰을 response 해주면 됨.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecretKey secretKey = new SecretKey();
        log.info("successfulAuthentication 실행됨: 인증이 완료됨.......!");

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject(principalDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.EXPIRATION_TIME))//1000=1초
                .withClaim("id", principalDetails.getMember().getId())// member 의 id 정보
                .withClaim("username", principalDetails.getMember().getUsername())// member 의 username 정보
                .sign(Algorithm.HMAC512(secretKey.getKey()));

        response.addHeader("Authorization", "Bearer "+ jwtToken);// Authorization: Bearer $token
    }
}
