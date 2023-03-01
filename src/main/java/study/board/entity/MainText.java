package study.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainText {
    @Id @GeneratedValue
    @Column(name = "mainText_id")
    private Long id;

    private String textMain;
    @OneToOne(mappedBy = "mainText",fetch = FetchType.LAZY)
    private Posts posts;

    public MainText(String textMain) {
        this.textMain = textMain;
    }
}
