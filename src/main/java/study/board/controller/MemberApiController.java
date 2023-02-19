package study.board.controller;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.board.dto.MemberDto;
import study.board.entity.Member;
import study.board.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberApiController {

    private final MemberRepository memberRepository;
    @GetMapping("/api/members")
    public Result memberList() {
        List<Member> members = memberRepository.findAll();
        List<MemberDto> collect = members.stream().map(m -> new MemberDto(m.getUsername())).collect(Collectors.toList());

        return new Result(collect, collect.size());
    }

    @PostMapping("/api/register")
    public CreateMemberResponse register(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member(request.getName());
        Member save = memberRepository.save(member);
        return new CreateMemberResponse(save.getId());
    }


    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
        private int count;

    }

    @Data
    private static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
    @Data
    private static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }

    @PostConstruct
    public void init(){
        for (int i = 1; i < 10; i++) {
            memberRepository.save(new Member("user" + i));
        }
    }
}
