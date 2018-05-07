package com.killerchess.core.controllers.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping("/")
    public String index() {
        return "Hello world!";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/register")
    public int register() {
        //here need to "return" or somehow "changeScene" in order to give User possibility to see the register screne
        return 1;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
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

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public void login() {
        //same as register GET method, but login
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
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
