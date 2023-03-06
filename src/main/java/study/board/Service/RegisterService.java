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
        if(password1.equals(password2)&& findUser.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
}
