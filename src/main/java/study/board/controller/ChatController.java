package study.board.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.board.dto.CommentDto;
import study.board.websocket.ChatRoom;
import study.board.websocket.dto.ChatDto;
import study.board.websocket.service.ChatService;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ChatRoom createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }

    @DeleteMapping
    public void deleteRoom(@RequestParam String name) {
        chatService.deleteRoom(name);
    }

    @GetMapping
    public  Result findAllRoom() {
        List<ChatDto> allRoom = chatService.findAllRoom();
        return new Result(allRoom, allRoom.size());
    }

    @Data
    @AllArgsConstructor
    static class Result {
        private List<ChatDto> content;
        private int totalElements;
    }
}