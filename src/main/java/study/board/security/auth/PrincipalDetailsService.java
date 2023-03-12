package study.board.security.auth;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import study.board.entity.Member;
import study.board.repository.MemberRepository;

import java.util.Optional;

// http//localhost:8080/login 시 실행인데 SecurityConfig 에서 막아놔서 직접 때려주는 필터를 따로 만들어야한다
// 그게 JwtAuthenticationFilter
@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("PrincipalDetailsService:loadUserByUsername 실행됨");
        Optional<Member> member = memberRepository.findByUsername(username);
        return new PrincipalDetails(member.get());
    }
}
