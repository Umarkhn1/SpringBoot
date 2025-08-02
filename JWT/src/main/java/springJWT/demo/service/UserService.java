package springJWT.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springJWT.demo.Repository.UserRepository;
import springJWT.demo.domain.User;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User save(User user) {
     String password = passwordEncoder.encode(user.getPassword());
     user.setPassword(password);
        return userRepository.save(user);
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
