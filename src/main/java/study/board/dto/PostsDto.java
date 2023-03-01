package study.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class PostsDto {
    private Long id;
    private Long memberId;
    private String writer;
    private String title;
    private Integer likes;

    public PostsDto(Long id, Long memberId, String writer, String title, Integer likes) {
        this.id = id;
        this.memberId = memberId;
        this.writer = writer;
        this.title = title;
        this.likes = likes;
    }


}
