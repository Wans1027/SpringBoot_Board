package study.board.entity;

import jakarta.persistence.*;
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
    private String username;
    @OneToMany(mappedBy = "member")
    private List<Posts> posts = new ArrayList<>();
    @Embedded
    private Address address;

    protected Member() {
    }

    public Member(String username) {
        this.username = username;
    }
}
