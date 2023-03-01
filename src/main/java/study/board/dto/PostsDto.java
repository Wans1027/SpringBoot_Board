package study.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class PostsDto {
    private Long id;
    private Long memberId;
    private String writer;
    private String title;
    private Integer likes;

}
