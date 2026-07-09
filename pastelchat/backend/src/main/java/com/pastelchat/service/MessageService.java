package com.pastelchat.service;
import com.pastelchat.model.Message;
import com.pastelchat.model.User;
import com.pastelchat.repository.MessageRepository;
import com.pastelchat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository msgRepo;
    private final UserRepository userRepo;
    @Value("${app.upload.dir}")
    private String uploadDir;

    public Map<String, Object> sendMessage(Long senderId, Long receiverId, String content, MultipartFile file) {
        User sender = userRepo.findById(senderId).orElseThrow();
        User receiver = userRepo.findById(receiverId).orElseThrow();
        Message.MessageBuilder builder = Message.builder()
            .sender(sender).receiver(receiver).content(content);
        if (file != null && !file.isEmpty()) {
            String mediaType = detectType(file.getContentType());
            String fileName = System.currentTimeMillis() + "_" +
                file.getOriginalFilename().replaceAll("[^a-zA-Z0-9._\\-]", "_");
            try {
                Path dir = Paths.get(uploadDir);
                Files.createDirectories(dir);
                Files.copy(file.getInputStream(), dir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) { throw new RuntimeException("File upload failed"); }
            builder.mediaUrl("/uploads/" + fileName).mediaType(mediaType).mediaName(file.getOriginalFilename());
        }
        return toMap(msgRepo.save(builder.build()));
    }

    public List<Map<String, Object>> getConversation(Long userAId, Long userBId) {
        User a = userRepo.findById(userAId).orElseThrow();
        User b = userRepo.findById(userBId).orElseThrow();
        msgRepo.markAllRead(b, a);
        return msgRepo.findConversation(a, b).stream().map(this::toMap).toList();
    }

    public long getUnreadCount(Long senderId, Long receiverId) {
        User sender = userRepo.findById(senderId).orElseThrow();
        User receiver = userRepo.findById(receiverId).orElseThrow();
        return msgRepo.countBySenderAndReceiverAndReadFalse(sender, receiver);
    }

    private String detectType(String ct) {
        if (ct == null) return "file";
        if (ct.startsWith("image/")) return "image";
        if (ct.startsWith("video/")) return "video";
        return "file";
    }

    private Map<String, Object> toMap(Message m) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", m.getId());
        map.put("senderId", m.getSender().getId());
        map.put("senderName", m.getSender().getName());
        map.put("receiverId", m.getReceiver().getId());
        map.put("content", m.getContent());
        map.put("mediaUrl", m.getMediaUrl());
        map.put("mediaType", m.getMediaType());
        map.put("mediaName", m.getMediaName());
        map.put("read", m.isRead());
        map.put("sentAt", m.getSentAt());
        return map;
    }
}