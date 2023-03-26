package study.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.el.stream.Optional;

@Entity
@Getter

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainText {
    @Id @GeneratedValue
    @Column(name = "mainText_id")
    private Long id;

    private String textMain;
    @OneToOne(mappedBy = "mainText",fetch = FetchType.LAZY)//읽기전용
    private Posts posts;

    private String image;

    public MainText(String textMain) {
        this.textMain = textMain;
    }

    public void setTextMain(String textMain) {
        this.textMain = textMain;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
