package study.board.controller;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
    //생성자 주입
    private final MemberRepository memberRepository;
    private final LoginService loginservice;
    private final RegisterService registerService;


    //회원이름 전부 조회
    @GetMapping("/members")
    public Result memberList() {
        List<String> members = memberRepository.findUsernameList();
        List<MemberDto> collect = members.stream().map(m -> new MemberDto(m)).collect(Collectors.toList());

        return new Result(collect, collect.size());
    }
    //회원가입
    @PostMapping("/register")
    public CreateMemberResponse register(@RequestBody @Valid MemberRegisterRequest request) {
        boolean valid = registerService.validUserForm(request.name,request.password1, request.password2);//두개의 비밀번호가 일치하는지 검사
        if (valid) {
            Member member = new Member(request.getName(), registerService.regBcrypt(request.getPassword1()));
            Member save = memberRepository.save(member);
            return new CreateMemberResponse(save.getId());
        }else{
            throw new IllegalArgumentException("Not equal password");
        }
    }
    //로그인
    @PostMapping("/login")
    public CreateMemberResponse login(@RequestBody @Valid CreateMemberRequest request){
        Member loginMember = loginservice.loginBcrypt(request.getName(), request.getPassword());// 입력된 정보가 회원에 있나 찾아봄
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
        @NotBlank
        private String password;
    }

    @Data
    private static class MemberRegisterRequest {
        @NotEmpty
        private String name;
        @NotEmpty
        @Size(min = 4, max = 12, message = "비밀번호는 4자리 이상 12자리 미만입니다.")
        private String password1;
        @NotEmpty
        private String password2;
    }
}
