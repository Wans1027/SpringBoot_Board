package study.board.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.entity.Member;
import study.board.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    //회원수정
    public void memberUpdate(Long id, String username) {
        Optional<Member> member = memberRepository.findById(id);
        Member getMember = member.get();
        getMember.setUsername(username);
    }




}
