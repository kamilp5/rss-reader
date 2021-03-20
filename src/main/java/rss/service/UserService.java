package rss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email).orElseThrow();
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return getUserByEmail(authentication.getName());
        }
        return null;
    }

}
