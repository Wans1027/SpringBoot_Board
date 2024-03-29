package study.board.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import study.board.Service.MemberService;
import study.board.dto.MemberDto;
import study.board.entity.Member;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j

public class MemberApiController {
    //생성자 주입
    private final MemberService memberService;


    //회원이름 전부 조회
    @GetMapping("/api/members")
    public Result memberList() {
        List<String> members = memberService.findMemberNameList();
        List<MemberDto> collect = members.stream().map(m -> new MemberDto(m)).collect(Collectors.toList());

        return new Result(collect, collect.size());
    }
    //회원가입

    @PostMapping("/register")
    public CreateMemberResponse register(@RequestBody @Valid MemberRegisterRequest request) {
        Member member = memberService.ValidAndBcryptReg(request.username, request.password1, request.password2);
        return new CreateMemberResponse(member.getId());
    }

    //로그인
    /*@PostMapping("/login")
    public CreateMemberResponse login(@RequestBody @Valid CreateMemberRequest request){
        Member loginMember = memberService.loginBcrypt(request.getName(), request.getPassword());// 입력된 정보가 회원에 있나 찾아봄
        if(loginMember == null){
            throw new IllegalArgumentException("사용자 정보가 일치하지 않음.");
        }else {
            return new CreateMemberResponse(loginMember.getId());
        }
    }*/
    @GetMapping("/api/member/{memberId}")
    public MemberDto getMember(@PathVariable long memberId){
        Member member = memberService.findByIdService(memberId).orElseThrow();
        return new MemberDto(member.getUsername(), member.getNickName());
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
        private String username;
        @NotEmpty
        @Size(min = 4, max = 12, message = "비밀번호는 4자리 이상 12자리 미만입니다.")
        private String password1;
        @NotEmpty
        private String password2;
    }
}
