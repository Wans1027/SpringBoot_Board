package study.board.controller;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import study.board.Service.LoginService;
import study.board.dto.MemberDto;
import study.board.entity.Member;
import study.board.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class MemberApiController {

    private final MemberRepository memberRepository;
    private final LoginService loginservice;



    @GetMapping("/members")
    public Result memberList() {
        List<Member> members = memberRepository.findAll();
        List<MemberDto> collect = members.stream().map(m -> new MemberDto(m.getUsername())).collect(Collectors.toList());

        return new Result(collect, collect.size());
    }

    @PostMapping("/register")
    public CreateMemberResponse register(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member(request.getName(), request.getPassword());
        Member save = memberRepository.save(member);
        return new CreateMemberResponse(save.getId());
    }

    @PostMapping("/login")
    public CreateMemberResponse login(@RequestBody @Valid CreateMemberRequest request){
        Member loginMember = loginservice.Login(request.getName(), request.getPassword());
        if(loginMember == null){
            throw new IllegalArgumentException("사용자 정보가 일치하지 않음.");
        }else {
            return new CreateMemberResponse(loginMember.getId());
        }
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
        @NotEmpty
        private String password;
    }

    @PostConstruct
    public void init(){
        for (int i = 1; i < 10; i++) {
            memberRepository.save(new Member("user" + i,"1234"+i+"!"));
        }

    }
}
