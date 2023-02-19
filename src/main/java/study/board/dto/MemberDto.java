package study.board.dto;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class MemberDto {
    private String username;

    public MemberDto(String username) {
        this.username = username;
    }
}
