package com.killerchess.core.controllers.user;

import com.killerchess.core.dto.UserDTO;
import com.killerchess.core.services.RegisterService;
import com.killerchess.core.services.UserService;
import com.killerchess.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private static final String REGISTER_PATH = "/register";
    private static final String LOGIN_PATH = "/login";
    private static final String USERS_PATH = "/users";


    private final UserService userService;
    private final RegisterService registerService;

    @Autowired
    public UserController(UserService userService, RegisterService registerService) {
        this.userService = userService;
        this.registerService = registerService;
    }

    @RequestMapping(method = RequestMethod.GET, value = USERS_PATH)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        // TODO here need to "return" or somehow "changeScene" in order to give User possibility to see the register screne
        List<User> usersList = userService.findAll();
        List<UserDTO> usersDTOList = new ArrayList<>();
        for (User user : usersList) {
            UserDTO tempUserDTO = new UserDTO();
            tempUserDTO.setUsername(user.getLogin());
            usersDTOList.add(tempUserDTO);
        }
        return new ResponseEntity<>(usersDTOList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = REGISTER_PATH)
    public ResponseEntity register(@RequestParam(value = "username") String name,
                                   @RequestParam(value = "password") String password,
                                   HttpServletRequest request) {
        try {
            if (!registerService.isPasswordValid(password)) {
                return new ResponseEntity<>("Wrong password. Password requirements: one big and small character, " +
                        "one digit, one special sign, 8 up to 25 characters.",
                        HttpStatus.NOT_ACCEPTABLE);
            }
            if (!registerService.isValidUser(name, password)) {
                return new ResponseEntity<>("Wrong user or password. Please provide password different from your login",
                        HttpStatus.NOT_ACCEPTABLE);
            }
            if (userService.existsLogin(name)) {
                return new ResponseEntity<>("User with that login already exists.", HttpStatus.NOT_ACCEPTABLE);
            }
            request.getSession();
            User user = new User(name);
            user.setHashedPassword(password);
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = LOGIN_PATH)
    public ResponseEntity login(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("username", request.getSession().getAttribute("username").toString());
        return new ResponseEntity(headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = LOGIN_PATH)
    public ResponseEntity login(@RequestParam(value = "username") String name,
                                @RequestParam(value = "password") String password,
                                HttpServletRequest request) {
        try {
            if (!userService.existsLogin(name)) {
                return new ResponseEntity<>("User with that login doesn't exist.", HttpStatus.NOT_ACCEPTABLE);
            }
            HttpSession session = request.getSession();
            User user = new User(name);
            User existingUser = userService.find(user);
            user.setHashedPassword(password, existingUser.getSalt());
            if (user.getPassword().equals(existingUser.getPassword())) {
                session.setAttribute("username", user.getLogin());
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity<>("Wrong password.", HttpStatus.NOT_ACCEPTABLE);


        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
