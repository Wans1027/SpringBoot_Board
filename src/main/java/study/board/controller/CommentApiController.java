package study.board.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.board.dto.CommentDto;
import study.board.entity.CommentsTable;
import study.board.entity.Member;
import study.board.entity.Posts;
import study.board.repository.CommentRepository;
import study.board.repository.MemberRepository;
import study.board.repository.PostsRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostsRepository postsRepository;


    //댓글쓰기
    @PostMapping("/api/comment")
    public CommentResponse writeComment(@RequestBody @Valid CommentRequest request){
        //게시글은 쓰는것 보다 조회하는 수가 훨얼씬 많다. 그래서 쓸때는 쿼리여려개를 하고 읽을때는 한번에 가져오자
        return getCommentResponse(request);
    }

    private CommentResponse getCommentResponse(CommentRequest request) {
        Optional<Member> member = memberRepository.findById(request.memberId);
        Optional<Posts> post = postsRepository.findById(request.postsId);
        CommentsTable commentsTable;
        if(request.hierarchy == 1){ //대댓글이라면
            //그룹의 가장높은 order 를 DB 에서 찾고 +1을 하여 대댓글을 저장
            Long order = commentRepository.findMaxCommentOrder(request.postsId, request.group);
            commentsTable = new CommentsTable(post.orElseThrow(), member.orElseThrow(), request.comment, request.hierarchy, order+1, request.group);
        } else{//댓글이라면
            commentsTable = new CommentsTable(post.orElseThrow(), member.orElseThrow(), request.comment, request.hierarchy, request.order, request.group);
        }
        CommentsTable savedCommentsTable = commentRepository.save(commentsTable);
        return new CommentResponse(savedCommentsTable.getId());
    }

    //댓글 조회
    @GetMapping("/api/comment/{postId}")
    public Result getComments(@PathVariable("postId") Long postId) {
        //정렬된 댓글을 가져오고
        List<CommentsTable> alignedComments = commentRepository.findAlignedCommentByPostId(postId);
        //Dto 로 변환한다
        List<CommentDto> commentsDto = alignedComments.stream()
                .map(m -> new CommentDto(m.getMember().getId(),m.getMember().getUsername(), m.getCom(), m.getHierarchy(), m.getOrders(), m.getGrp(), m.getLikes(), m.getCreatedDate())).toList();
        return new Result(commentsDto, commentsDto.toArray().length);
    }

    @Data
    @AllArgsConstructor
    static class Result {
        private List<CommentDto> content;
        private int totalElements;
    }
    @Data
    private static class CommentRequest {
        private Long postsId;
        private Long memberId;
        private String comment;
        private Long hierarchy;
        private Long order;
        private Long group;
    }

    @Data
    private static class CommentResponse {
        private Long id;
        CommentResponse(Long id){
            this.id = id;
        }
    }
}
