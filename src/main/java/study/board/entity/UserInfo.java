package study.board.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfo {

    private String residence;
    @Email(message = "Email 형식에 맞지 않습니다.")
    private String email;
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 형식에 맞지 않습니다.")
    private String phoneNumber;

    public UserInfo(String residence, String email, String phoneNumber) {
        this.residence = residence;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
