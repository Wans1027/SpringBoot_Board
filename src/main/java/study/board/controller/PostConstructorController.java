package study.board.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import study.board.entity.Comment;
import study.board.entity.MainText;
import study.board.entity.Member;
import study.board.entity.Posts;
import study.board.repository.CommentRepository;
import study.board.repository.MainTextRepository;
import study.board.repository.MemberRepository;
import study.board.repository.PostsRepository;

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

    @PostConstruct
    public void init(){
        for (int i = 1; i < 11; i++) {
            memberRepository.save(new Member("user"+i,"1234"));
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
        log.info("test----------------------------------------------------------");
        List<Comment> commentByPostId = commentRepository.findCommentByPostId(1L);
        Comment comment1 = commentByPostId.stream().findFirst().get();
        log.info("댓글: \"{}\", 댓글입력자: {}", comment1.getComment(), comment1.getMember().getUsername());

    }
}
