package com.pastelchat.service;
import com.pastelchat.model.User;
import com.pastelchat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#_\\-])[A-Za-z\\d@$!%*?&#_\\-]{8,}$"
    );
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[6-9]\\d{9}$");
    private static final String[] AVATAR_COLORS = {
        "#c4b5fd","#fbcfe8","#bbf7d0","#fed7aa","#bae6fd","#fde68a","#e9d5ff","#ccfbf1"
    };
    private int colorIndex = 0;

    public Map<String, Object> register(String name, String email, String phone, String password) {
        Map<String, Object> result = new LinkedHashMap<>();
        List<String> errors = new ArrayList<>();
        if (name == null || name.trim().length() < 2)
            errors.add("Name must be at least 2 characters.");
        if (email == null || !email.matches("^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$"))
            errors.add("Invalid email address.");
        else if (userRepo.existsByEmail(email))
            errors.add("Email already registered.");
        if (phone == null || !PHONE_PATTERN.matcher(phone).matches())
            errors.add("Phone must be a valid 10-digit Indian mobile number.");
        else if (userRepo.existsByPhone(phone))
            errors.add("Phone number already registered.");
        if (password == null || !PASSWORD_PATTERN.matcher(password).matches())
            errors.add("Password must be 8+ chars with uppercase, lowercase, digit & special character.");
        if (!errors.isEmpty()) {
            result.put("success", false);
            result.put("errors", errors);
            return result;
        }
        String color = AVATAR_COLORS[colorIndex % AVATAR_COLORS.length];
        colorIndex++;
        User user = User.builder()
            .name(name.trim()).email(email.trim().toLowerCase())
            .phone(phone.trim()).password(passwordEncoder.encode(password))
            .avatarColor(color).build();
        User saved = userRepo.save(user);
        result.put("success", true);
        result.put("user", safeUser(saved));
        return result;
    }

    public Map<String, Object> login(String email, String password) {
        Map<String, Object> result = new LinkedHashMap<>();
        Optional<User> opt = userRepo.findByEmail(email == null ? "" : email.trim().toLowerCase());
        if (opt.isEmpty() || !passwordEncoder.matches(password, opt.get().getPassword())) {
            result.put("success", false);
            result.put("errors", List.of("Invalid email or password."));
            return result;
        }
        User user = opt.get();
        user.setOnline(true);
        userRepo.save(user);
        result.put("success", true);
        result.put("user", safeUser(user));
        return result;
    }

    public void setOffline(Long userId) {
        userRepo.findById(userId).ifPresent(u -> { u.setOnline(false); userRepo.save(u); });
    }

    public List<Map<String, Object>> getAllUsers() {
        return userRepo.findAll().stream().map(this::safeUser).toList();
    }

    public Map<String, Object> safeUser(User u) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("id", u.getId());
        m.put("name", u.getName());
        m.put("email", u.getEmail());
        m.put("phone", u.getPhone());
        m.put("avatarColor", u.getAvatarColor());
        m.put("online", u.isOnline());
        m.put("createdAt", u.getCreatedAt());
        return m;
    }
}