package study.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@DynamicInsert//columnDefault 쓰려면 써야됨
public class Posts {
    @Id
    @GeneratedValue
    @Column(name = "posts_id")
    private Long id;
    @NotEmpty
    private String title;

    private String mainText;
    //@ColumnDefault("10")
    private int likes = 10;
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
