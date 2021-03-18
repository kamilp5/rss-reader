package rss.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rss.service.UserService;

import java.security.Principal;

@Controller
public class UserController {


    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/1")
    public void test(){
        System.out.println("get");

    }

    @RequestMapping("/login")
    public Principal user(Principal user){
        System.out.println("login");
        System.out.println(user);
        return user;
    }
}
