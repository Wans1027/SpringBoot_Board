package study.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Entity
@Getter
public class Posts {
    @Id
    @GeneratedValue
    @Column(name = "posts_id")
    private Long id;
    @NotEmpty
    private String title;
    private String mainText;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected Posts(){}

    public Posts(String title, String mainText, Member member){
        this.title = title;
        this.mainText = mainText;
        this.member = member;
    }


}
