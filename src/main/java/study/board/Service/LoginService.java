package study.board.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import study.board.entity.Member;
import study.board.repository.MemberRepository;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    public Member Login(String username, String password){
        Optional<Member> findMember = memberRepository.findByUsername(username);
        return findMember.filter(m -> m.getPassword().equals(password)).orElse(null);
    }
}
