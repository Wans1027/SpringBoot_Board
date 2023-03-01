package study.board.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import study.board.entity.Member;
import study.board.entity.Posts;
import study.board.repository.MemberRepository;
import study.board.repository.PostsRepository;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostConstructorController {
    private final PostsRepository postsRepository;
    private final MemberRepository memberRepository;

    @PostConstruct
    public void init(){
        for (int i = 1; i < 11; i++) {
            memberRepository.save(new Member("user"+i,"1234"));
        }
        Optional<Member> firstMember = memberRepository.findAll().stream().findFirst();
        for (int i = 1; i < 11; i++) {
            postsRepository.save(new Posts("게시글 "+i, "게시글본문",firstMember.get()));
        }
    }
}
