package study.board.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfo {

    private String residence;
    private String email;
    private String phoneNumber;

    public UserInfo(String residence, String email, String phoneNumber) {
        this.residence = residence;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
