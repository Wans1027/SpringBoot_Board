package study.board.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Posts {
    @Id
    @GeneratedValue
    @Column(name = "posts_id")
    private Long id;
    private String title;
    private String mainText;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


}
