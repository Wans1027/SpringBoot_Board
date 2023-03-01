package study.board.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import study.board.entity.MainText;
import study.board.entity.Member;
import study.board.entity.Posts;
import study.board.repository.MainTextRepository;
import study.board.repository.MemberRepository;
import study.board.repository.PostsRepository;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostConstructorController {
    private final PostsRepository postsRepository;
    private final MemberRepository memberRepository;
    private final MainTextRepository mainTextRepository;

    @PostConstruct
    public void init(){
        for (int i = 1; i < 11; i++) {
            memberRepository.save(new Member("user"+i,"1234"));
        }
        Optional<Member> firstMember = memberRepository.findAll().stream().findFirst();

        for (int i = 1; i < 11; i++) {
            MainText mainText = new MainText("본문쓰");
            mainTextRepository.save(mainText);
            Posts posts = new Posts("게시글 " + i, firstMember.get(),mainText);
            postsRepository.save(posts);
        }




    }
}
