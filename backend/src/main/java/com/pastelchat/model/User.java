package com.pastelchat.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@Column(nullable = false, length = 100)
private String name;
@Column(nullable = false, unique = true, length = 150)
private String email;
@Column(nullable = false, length = 20)
private String phone;
@Column(nullable = false)
private String password;
@Column(name = "avatar_color", length = 20)
private String avatarColor = "#a78bfa";
@Column(name = "is_online")
private boolean online = false;
@Column(name = "created_at")
private LocalDateTime createdAt = LocalDateTime.now();
@Column(name = "last_seen")
private LocalDateTime lastSeen = LocalDateTime.now();
}
