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
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import study.board.dto.PostsDto;
import study.board.entity.MainText;
import study.board.entity.Member;
import study.board.entity.Posts;
import study.board.repository.MainTextRepository;
import study.board.repository.MemberRepository;
import study.board.repository.PostsRepository;
import study.board.security.auth.PrincipalDetails;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class PostsApiController {
    private final PostsRepository postsRepository;
    private final MemberRepository memberRepository;
    private final MainTextRepository mainTextRepository;


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

    /*@GetMapping("/posts/{id}") // 쿼리 두번
    public Result postListById(@PathVariable("id") Long id){
        List<PostsDto> postsDto = postsRepository.findPostByMember(memberRepository.findById(id).get()).stream()
                .map(PostsApiController::changePostsDto)
                .collect(Collectors.toList());
        return new Result(postsDto, postsDto.size());
    }*/

    //특정 id 회원이 쓴 모든글 조회
    @GetMapping("/postsV2/{id}") //쿼리 한번 fetchJoin
    public Result postListByIdV2(@PathVariable("id") Long id){
        List<Posts> posts = postsRepository.findAll().stream().filter(p -> p.getMember().getId().equals(id)).collect(Collectors.toList());
        List<PostsDto> postsDto = posts.stream()
                .map(PostsApiController::changePostsDto)
                .collect(Collectors.toList());
        return new Result(postsDto, postsDto.size());
    }

    //페이징 모든게시글 조회
    @GetMapping("/posts")
    public Page<PostsDto> loadAllPosts(Pageable pageable){
        Page<Posts> posts = postsRepository.findAll(pageable);
        Page<PostsDto> postsDto = posts.map(PostsApiController::changePostsDto);

        return postsDto;
    }
    //게시물 아이디를 넘기면 본문반환
    @GetMapping("/post/{id}")
    public Optional<PostDto> getSinglePostData(@PathVariable("id") Long id){
        Optional<Posts> post = postsRepository.findMainTextById(id);
        Optional<PostsDto> postsDto = post.map(PostsApiController::changePostsDto);
        return postsDto.map(p -> new PostDto(p, post.get().getMainText().getTextMain()));
    }

    private static PostsDto changePostsDto(Posts p) {
        return new PostsDto(p.getId(), p.getMember().getId(), p.getMember().getUsername(), p.getTitle(), p.getLikes(), p.getCreatedDate());
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
        private int count;

    }

    @Data
    @AllArgsConstructor
    static class PostDto{
        private PostsDto postsDto;
        private String textMain;
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
