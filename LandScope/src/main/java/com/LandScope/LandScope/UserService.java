package com.LandScope.LandScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	 @Autowired
	    private UserRepository userRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;

    public boolean registerUser(String fullName, String username, String password) {
        if (userRepository.existsByUsername(username)) {
            return false; // username already taken
        }
        User user = new User();
        user.setFullName(fullName);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        userRepository.save(user);
        return true;
    }
}
