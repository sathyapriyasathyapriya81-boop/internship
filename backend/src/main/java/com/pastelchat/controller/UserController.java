package com.pastelchat.controller;
import com.pastelchat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> body) {
        Map<String, Object> result = userService.register(
            body.get("name"), body.get("email"), body.get("phone"), body.get("password"));
        boolean ok = Boolean.TRUE.equals(result.get("success"));
        return ResponseEntity.status(ok ? 201 : 400).body(result);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        Map<String, Object> result = userService.login(body.get("email"), body.get("password"));
        boolean ok = Boolean.TRUE.equals(result.get("success"));
        return ResponseEntity.status(ok ? 200 : 401).body(result);
    }

    @PostMapping("/{id}/logout")
    public ResponseEntity<Void> logout(@PathVariable Long id) {
        userService.setOffline(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}