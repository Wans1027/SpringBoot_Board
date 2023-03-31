package study.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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
    public  List<ChatDto> findAllRoom() {
        List<ChatDto> allRoom = chatService.findAllRoom();
        return allRoom;
    }
}