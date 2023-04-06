package study.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    @NotEmpty

    private String username;
    @NotEmpty
    private String password;
    @OneToMany(mappedBy = "member")
    private List<Posts> posts = new ArrayList<>();
    /*@Embedded
    private UserInfo userInfo;*/
    @Column(length = 50)
    private String nickName = username;
    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<FcmToken> fcmTokens = new ArrayList<>();

    @Column(name = "activated")
    private boolean activated = true;

    private String roles; //USER, ADMIN


    protected Member() {
    }
    public void addFcmToken(FcmToken fcmToken){
        this.fcmTokens.add(fcmToken);
    }

    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Member(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
