package study.board.Service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import study.board.entity.Member;
import study.board.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final MemberRepository memberRepository;

    public boolean validUserForm(String userName, String password1, String password2) {
        Optional<Member> findUser = memberRepository.findByUsername(userName);
        return password1.equals(password2) && findUser.isEmpty();
    }

    /**
     * Bcrypt 비밀번호 암호화
     */
    public String regBcrypt(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
