package rss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rss.repository.UserRepository;
import rss.user.User;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User createUser(User user) {
        if (isEmailAvailable(user.getEmail())) {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }


    private boolean isEmailAvailable(String email) {
        return userRepository.getUserByEmail(email).isEmpty();
    }
    public User getUserByEmail(String email){
        return userRepository.getUserByEmail(email).orElseThrow();
    }


}
