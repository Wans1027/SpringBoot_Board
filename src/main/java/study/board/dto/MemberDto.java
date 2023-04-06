package study.board.dto;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class MemberDto {
    private String username;
    private String nickName;

    public MemberDto(String username) {
        this.username = username;
    }

    public MemberDto(String username, String nickName) {
        this.username = username;
        this.nickName = nickName;
    }
}
