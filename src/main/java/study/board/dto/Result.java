package study.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import study.board.websocket.dto.ChatDto;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class Result<T> {
    private List<T> content;
    private int totalElements;
}
