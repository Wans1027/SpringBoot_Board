package study.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.board.Service.MemberService;
import study.board.entity.Member;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;
    @Autowired MemberService memberService;

    @Test
    public void testMember(){
        Member member1 = new Member("userA");
        Member member2 = new Member("userB");
        memberRepository.save(member1);
        memberRepository.save(member2);
        List<Member> all = memberRepository.findAll();

        member1.setUsername("userC");
        for (Member member : all) {
            System.out.println("member.getUsername() = " + member.getUsername());
        }

    }
    @Test
    public void testMemberService(){
        Member member1 = new Member("userA");
        Member member2 = new Member("userB");
        memberRepository.save(member1);
        memberRepository.save(member2);
        List<Member> all = memberRepository.findAll();

        memberService.memberUpdate(member1.getId(), "userC");
        for (Member member : all) {
            System.out.println("member.getUsername() = " + member.getUsername());
        }

    }

}