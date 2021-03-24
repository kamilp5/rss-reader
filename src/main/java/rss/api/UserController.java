package rss.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rss.service.UserService;
import rss.user.User;
import rss.utils.UserAlreadyExistsException;

import java.security.Principal;

@RestController
public class UserController {


    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @RequestMapping("/login")
    public Principal user(Principal user){
        userService.getLoggedUser();
        return user;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handle(UserAlreadyExistsException e){
        return e.getMessage();
    }
}
