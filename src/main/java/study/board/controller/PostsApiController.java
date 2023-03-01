package study.board.controller;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import study.board.dto.PostsDto;
import study.board.entity.Member;
import study.board.entity.Posts;
import study.board.repository.MemberRepository;
import study.board.repository.PostsRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class PostsApiController {
    private final PostsRepository postsRepository;
    private final MemberRepository memberRepository;

    @PostMapping("/posts")
    public CreatePostResponse writePost(@RequestBody @Valid CreatePostRequest request){
        Optional<Member> member = memberRepository.findById(request.getMemberId());
        Posts posts = new Posts(request.getTitle(), request.getMainText(), member.orElseThrow());
        Posts save = postsRepository.save(posts);
        return new CreatePostResponse(save.getId());
    }

    @GetMapping("/posts/{id}") // 쿼리 두번
    public Result postListById(@PathVariable("id") Long id){
        List<Posts> posts = postsRepository.findPostByMember(memberRepository.findById(id).get());
        List<PostsDto> postsDto = posts.stream()
                .map(p -> new PostsDto(p.getId(),p.getMember().getId(), p.getMember().getUsername(),p.getTitle(),p.getLikes()))
                .collect(Collectors.toList());
        return new Result(postsDto, postsDto.size());
    }

    @GetMapping("/postsV2/{id}") //쿼리 한번 fetchJoin
    public Result postListByIdV2(@PathVariable("id") Long id){
        List<Posts> posts = postsRepository.findAll().stream().filter(p -> p.getMember().getId().equals(id)).collect(Collectors.toList());
        List<PostsDto> postsDto = posts.stream()
                .map(p -> new PostsDto(p.getId(), p.getMember().getId(), p.getMember().getUsername(), p.getTitle(),p.getLikes()))
                .collect(Collectors.toList());
        return new Result(postsDto, postsDto.size());
    }

    @GetMapping("/posts")
    public Result loadAllPosts(){
        List<Posts> posts = postsRepository.findAll();
        List<PostsDto> postDto = posts.stream()
                .map(p -> new PostsDto(p.getId(), p.getMember().getId(), p.getMember().getUsername(), p.getTitle(),p.getLikes()))
                .collect(Collectors.toList());
        return new Result(postDto, postDto.size());
    }

    @GetMapping("/posts-entity")
    public List<PostsDto> loadAllPostsTest(){
        List<Posts> posts = postsRepository.findAll();
        List<PostsDto> postDto = posts.stream()
                .map(p -> new PostsDto(p.getId(), p.getMember().getId(), p.getMember().getUsername(), p.getTitle(),p.getLikes()))
                .collect(Collectors.toList());
        return postDto;
    }


    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
        private int count;

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
