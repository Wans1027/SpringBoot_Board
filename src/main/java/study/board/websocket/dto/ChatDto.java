package study.board.websocket.dto;

import lombok.Data;

@Data
public class ChatDto {

    private String roomId;
    private String name;

    private Long count;

    public ChatDto(String roomId, String name, Long count) {
        this.roomId = roomId;
        this.name = name;
        this.count = count;
    }
}
