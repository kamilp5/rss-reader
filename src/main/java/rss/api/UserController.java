package rss.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import rss.service.UserService;
import rss.user.User;

import java.security.Principal;

@Controller
public class UserController {


    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/users")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @RequestMapping("/login")
    public Principal user(Principal user){
        return user;
    }
}
