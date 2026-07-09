package com.pastelchat.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Message {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "sender_id", nullable = false)
private User sender;
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "receiver_id", nullable = false)
private User receiver;
@Column(columnDefinition = "TEXT")
private String content;
@Column(name = "media_url", length = 500)
private String mediaUrl;
@Column(name = "media_type", length = 50)
private String mediaType;
@Column(name = "media_name", length = 255)
private String mediaName;
@Column(name = "is_read")
private boolean read = false;
@Column(name = "sent_at")
private LocalDateTime sentAt = LocalDateTime.now();
}
