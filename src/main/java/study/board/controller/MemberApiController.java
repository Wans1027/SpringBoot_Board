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
import study.board.Service.RegisterService;
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
    private final RegisterService registerService;



    @GetMapping("/members")
    public Result memberList() {
        List<Member> members = memberRepository.findAll();
        List<MemberDto> collect = members.stream().map(m -> new MemberDto(m.getUsername())).collect(Collectors.toList());

        return new Result(collect, collect.size());
    }

    @PostMapping("/register")
    public CreateMemberResponse register(@RequestBody @Valid MemberRegisterRequest request) {
        boolean valid = registerService.validUserForm(request.name,request.password1, request.password2);
        if (valid) {
            Member member = new Member(request.getName(), request.getPassword1());
            Member save = memberRepository.save(member);
            return new CreateMemberResponse(save.getId());
        }else{
            throw new IllegalArgumentException("Not equal password");
        }
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

    @Data
    private static class MemberRegisterRequest {
        @NotEmpty
        private String name;
        @NotEmpty
        private String password1;
        @NotEmpty
        private String password2;
    }

}
