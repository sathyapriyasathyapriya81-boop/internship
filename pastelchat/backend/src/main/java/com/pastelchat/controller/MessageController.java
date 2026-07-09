package com.pastelchat.controller;
import com.pastelchat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping(value = "/send", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> send(
            @RequestParam Long senderId,
            @RequestParam Long receiverId,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) MultipartFile file) {
        if ((content == null || content.isBlank()) && (file == null || file.isEmpty()))
            return ResponseEntity.badRequest().body(Map.of("error", "Message or file required."));
        return ResponseEntity.status(201).body(messageService.sendMessage(senderId, receiverId, content, file));
    }

    @GetMapping("/conversation")
    public ResponseEntity<List<Map<String, Object>>> conversation(
            @RequestParam Long userA, @RequestParam Long userB) {
        return ResponseEntity.ok(messageService.getConversation(userA, userB));
    }

    @GetMapping("/unread")
    public ResponseEntity<Map<String, Object>> unread(
            @RequestParam Long senderId, @RequestParam Long receiverId) {
        return ResponseEntity.ok(Map.of("unread", messageService.getUnreadCount(senderId, receiverId)));
    }
}