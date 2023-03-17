package study.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicInsert//columnDefault 쓰려면 써야됨
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Posts extends TimeEntity{
    @Id
    @GeneratedValue
    @Column(name = "posts_id")
    private Long id;
    @NotEmpty
    private String title;


    //@ColumnDefault("10")
    private int likes = 10;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    /**
     * name은 단순하게 매핑할 외래 키의 이름, 즉 컬럼 명을 만들어주는 것이며,
     * referencedColumnName은 해당 외래 키가 대상이 되는 테이블의 어떤 컬럼을 참조하는지를 지정해주는 것입니다.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mainText_id")
    private MainText mainText;

    @OneToMany(mappedBy = "comment",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment = new ArrayList<>();

    public Posts(String title, Member member, MainText mainText){
        this.title = title;
        this.member = member;
        this.mainText = mainText;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
