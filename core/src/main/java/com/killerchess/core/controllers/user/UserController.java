package com.killerchess.core.controllers.user;

import com.killerchess.core.services.UserService;
import com.killerchess.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private static final String REGISTER_PATH = "/register";
    private static final String LOGIN_PATH = "/login";

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }


    // To delete after testing
    @RequestMapping("/")
    public String index() {
        return "Hello world!";
    }

    @RequestMapping(method = RequestMethod.GET, value = REGISTER_PATH)
    public List<User> register() {
        //here need to "return" or somehow "changeScene" in order to give User possibility to see the register screne
        return service.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, value = REGISTER_PATH)
    public void register(@RequestParam(value = "username") String name,
                         @RequestParam(value = "encryptedPassword") String encryptedPassword) throws Exception {
        //here we need to "register" new user and maybe return info, that registration succeded or failed
        /*
        sample code:
        try {
            User newUser = new User();
            newUser.username = username;
            newUser.encryptedPassword = encryptedPassword;
            userRepository.save(newUser);
            }
        catch(Exception  e){

        }
        */
    }

    @RequestMapping(method = RequestMethod.GET, value = LOGIN_PATH)
    public void login() {
        //same as register GET method, but login
    }

    @RequestMapping(method = RequestMethod.POST, value = LOGIN_PATH)
    public void login(@RequestParam(value = "username") String name,
                      @RequestParam(value = "encryptedPassword") String encryptedPassword) throws Exception {
        //here we need to "login" our User or not (if something will go wrong) and of course depending on result, redirectng to main menu or to loginView once again
        /*
        sample code:
        try {
            User loggingUser = new User();
            newUser.username = username;
            newUser.encryptedPassword = encryptedPassword;
            Boolean giveCredentials = userRepository.exists(newUser);
            }
        catch(Exception  e){

        }
        */
    }
}
