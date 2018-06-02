package com.killerchess.core.controllers.user;

import com.killerchess.core.dto.UserDTO;
import com.killerchess.core.exceptions.UndefinedException;
import com.killerchess.core.services.RegisterService;
import com.killerchess.core.services.UserService;
import com.killerchess.core.user.User;
import com.killerchess.core.util.FieldNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserController {

    private static final String REGISTER_PATH = "/register";
    private static final String REGISTER_PATH_SAMPLE = "/registerSample";
    private static final String LOGIN_PATH = "/login";


    private final UserService service;
    private final RegisterService registerService;

    @Autowired
    public UserController(UserService userService, RegisterService registerService) {
        this.service = userService;
        this.registerService = registerService;
    }


    @RequestMapping(method = RequestMethod.GET, value = REGISTER_PATH)
    public List<User> register() {
        //here need to "return" or somehow "changeScene" in order to give User possibility to see the register screne
        return service.findAll();
    }

    //This method is example. We can use HttpServletResponse as a method parameter instead of ResponseEntity.
    //It's depends of situation. If we want to have a file in response or sth like that, we will user HttpServletResponse.
    @RequestMapping(method = RequestMethod.GET, value = REGISTER_PATH_SAMPLE)
    public ResponseEntity register(HttpServletRequest request) {
        try {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(request.getParameter(FieldNames.USERNAME.getName()));
            userDTO.setPassword(request.getParameter(FieldNames.PASSWORD.getName()));
            registerService.isValidUser(userDTO);
        } catch (Throwable e) {
            UndefinedException undefinedException = new UndefinedException(e);
            System.out.println(undefinedException.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    public void register(@RequestParam(value = "username") String name,
                         @RequestParam(value = "encryptedPassword") String encryptedPassword) {
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
                      @RequestParam(value = "encryptedPassword") String encryptedPassword) {
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
