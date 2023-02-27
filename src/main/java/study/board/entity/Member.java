package study.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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
    @Embedded
    private Address address;

    protected Member() {
    }

    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
