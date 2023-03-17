package study.board.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import study.board.Service.MemberService;
import study.board.entity.*;
import study.board.repository.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostConstructorController {
    private final PostsRepository postsRepository;
    private final MemberRepository memberRepository;
    private final MainTextRepository mainTextRepository;
    private final CommentRepository commentRepository;

    private final MemberService memberService;

    @PostConstruct
    public void init(){
        /*for (int i = 1; i < 11; i++) {
            memberRepository.save(new Member("user"+i, memberService.regBcrypt("1234")));
        }
        Optional<Member> firstMember = memberRepository.findAll().stream().findFirst();

        for (int i = 1; i < 5; i++) {
            MainText mainText = new MainText("본문쓰"+i);
            mainTextRepository.save(mainText);
            Posts posts = new Posts("게시글 " + i, firstMember.get(),mainText);
            postsRepository.save(posts);
        }

        //게시판테이블 1번 게시물에 회원2가 댓글을 담
        Comment comment = new Comment("댓글1", postsRepository.findAll().stream().findFirst().orElseThrow(), memberRepository.findById(2L).orElseThrow());
        commentRepository.save(comment);
        List<Comment> commentByPostId = commentRepository.findCommentByPostId(1L);
        Comment comment1 = commentByPostId.stream().findFirst().get();
        log.info("댓글: \"{}\", 댓글입력자: {}", comment1.getComment(), comment1.getMember().getUsername());*/

    }
}
