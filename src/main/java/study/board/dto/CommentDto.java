package study.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto {
    private Long memberId;
    private String userName;
    private String comment;
    private Long hierarchy;
    private Long order;
    private Long group;
    private Long like;
    private String CreatedDate;
}
