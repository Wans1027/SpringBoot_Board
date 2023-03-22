package study.board.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import study.board.Service.PostService;
import study.board.dto.PostsDto;
import study.board.entity.MainText;
import study.board.entity.Member;
import study.board.entity.Posts;
import study.board.repository.MainTextRepository;
import study.board.repository.MemberRepository;
import study.board.repository.PostsRepository;
import study.board.security.auth.PrincipalDetails;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class PostsApiController {
    private final PostsRepository postsRepository;
    private final MemberRepository memberRepository;
    private final MainTextRepository mainTextRepository;

    private final PostService postService;


    //게시물쓰기
    @PostMapping("/posts")
    public CreatePostResponse writePost(@RequestBody @Valid CreatePostRequest request, Authentication authentication){
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        log.info("userid = {}",principal.getMember().getId());
        if (request.getMemberId().equals(principal.getMember().getId())) {
            Optional<Member> member = memberRepository.findById(request.getMemberId());
            MainText mainText = mainTextRepository.save(new MainText(request.getMainText()));
            Posts posts = new Posts(request.getTitle(), member.orElseThrow(), mainText);
            Posts save = postsRepository.save(posts);
            return new CreatePostResponse(save.getId());
        } else {
            throw new IllegalArgumentException("TokenID와 요청 ID가 일치하지 않습니다.");
        }
    }

    //게시물 수정
    @ResponseBody
    @Transactional
    @PatchMapping("/posts/{id}")
    public Map<String, Long> rewritePost(@RequestBody @Valid CreatePostRequest request, Authentication authentication, @PathVariable("id") Long postId){
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        log.info("userid = {}",principal.getMember().getId());
        if (request.getMemberId().equals(principal.getMember().getId())) {
            Optional<Posts> post = postsRepository.findById(postId);
            post.orElseThrow().setTitle(request.getTitle());
            post.orElseThrow().getMainText().setTextMain(request.getMainText());
            Map<String,Long> responseId = new HashMap<>();
            responseId.put("id", postId);
            return responseId;
        } else {
            throw new IllegalArgumentException("TokenID와 요청 ID가 일치하지 않습니다.");
        }
    }


    //특정 id 회원이 쓴 모든글 조회
    @GetMapping("/posts/{id}") //쿼리 한번 fetchJoin
    public Result postListByIdV2(@PathVariable("id") Long id){
        List<Posts> posts = postsRepository.findAll().stream().filter(p -> p.getMember().getId().equals(id)).toList();
        List<PostsDto> postsDto = posts.stream()
                .map(PostsApiController::changePostsDto)
                .collect(Collectors.toList());
        Collections.reverse(postsDto);
        return new Result(postsDto, postsDto.size());
    }

    //페이징 모든게시글 조회
    @GetMapping("/posts")
    public Page<PostsDto> loadAllPosts(Pageable pageable){
        Page<Posts> posts = postsRepository.findAll(pageable);

        return posts.map(PostsApiController::changePostsDto);
    }
    //게시물 아이디를 넘기면 본문반환
    @GetMapping("/post/{id}")
    public PostDto getSinglePostData(@PathVariable("id") Long id){
        Optional<Posts> post = postsRepository.findMainTextById(id);
        Optional<PostsDto> postsDto = post.map(PostsApiController::changePostsDto);
        return getPostDto(post.orElseThrow(), postsDto.orElseThrow());
    }

    private PostDto getPostDto(Posts post, PostsDto postsDto) {
        return new PostDto(postsDto, post.getMainText().getTextMain(),
                (post.getMainText().getImage() == null)? " ": post.getMainText().getImage().substring(1, post.getMainText().getImage().length() - 1));
    }

    //좋아요++
    @GetMapping("/post/like/{id}")
    public void upLikes(@PathVariable("id") Long id){
        Optional<Posts> post = postsRepository.findById(id);
        post.orElseThrow().setLikes(post.get().getLikes() + 1);
    }

    private static PostsDto changePostsDto(Posts p) {

        return new PostsDto(p.getId(), p.getMember().getId(), p.getMember().getUsername(), p.getTitle(), p.getLikes(), p.getCreatedDate());
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T content;
        private int totalElements;

    }

    @Data
    @AllArgsConstructor
    static class PostDto{
        private PostsDto postsDto;
        private String textMain;
        private String images;

    }

    @Data
    private static class CreatePostRequest {

        private Long memberId;
        @NotEmpty
        private String title;
        @NotEmpty
        private String mainText;
    }

    @Data
    private static class CreatePostResponse {

        private Long id;
        CreatePostResponse(Long id){
            this.id = id;
        }
    }


}
