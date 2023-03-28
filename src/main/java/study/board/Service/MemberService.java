package study.board.Service;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.entity.FcmToken;
import study.board.entity.Member;
import study.board.repository.FcmTokenRepository;
import study.board.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FcmTokenRepository fcmTokenRepository;

    //회원수정
    public void memberUpdate(Long id, String username) {
        //변경감지
        Optional<Member> member = findByIdService(id);
        Member getMember = member.orElseThrow(()-> new IllegalArgumentException("회원을 찾을 수 없음"));
        getMember.setUsername(username);

    }
    public void addFCMToken(Long memberId, String fcmToken){
        Optional<Member> member = memberRepository.findById(memberId);
        FcmToken token = new FcmToken(member.orElseThrow(), fcmToken);
        fcmTokenRepository.save(token);
    }

    private Optional<Member> findByIdService(Long id) {
        return memberRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<String> findMemberNameList(){
        return memberRepository.findUsernameList();
    }

    @Transactional(readOnly = true)
    //DB에 요청된 유저가 원래 있던 유저인지 검사하고 비밀번호 1,2 가 일치하는지 검사
    public boolean RegisterValidUserForm(String userName, String password1, String password2) {
        Optional<Member> findUser = memberRepository.findByUsername(userName);
        return password1.equals(password2) && findUser.isEmpty();
    }

    /**
     * Bcrypt 비밀번호 암호화
     */
    /*public String regBcrypt(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    @Transactional(readOnly = true)
    public Member loginBcrypt(String username, String password){
        Optional<Member> findMember = memberRepository.findByUsername(username);
        return findMember.filter(m -> BCrypt.checkpw(password, m.getPassword())).orElse(null);
    }*/

    public Member ValidAndBcryptReg(String userName, String password1,String password2){
        boolean valid = RegisterValidUserForm(userName, password1, password2);
        if (valid){
            Member member = new Member(userName, bCryptPasswordEncoder.encode(password1),"ROLE_USER");
            memberRepository.save(member);
            return member;
        }else {
            throw new IllegalArgumentException("Not equal password Or UserName already exist");
        }

    }

    public Member save(Member member){
        return memberRepository.save(member);
    }

}
