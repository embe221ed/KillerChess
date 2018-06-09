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
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private static final String REGISTER_PATH = "/register";
    private static final String LOGIN_PATH = "/login";
    public static final String GET_LOGIN_PATH = "/getLogin";
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
            request.getSession();
            User user = new User();
            user.setLogin(name);
            user.setPassword(password);
            if (!userService.existsUser(user)) {
                if (registerService.isValidUser(user)) {
                    userService.save(user);
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Wrong password or login!", HttpStatus.NOT_ACCEPTABLE);
                }
            } else {
                return new ResponseEntity<>("User with that login already exists", HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = GET_LOGIN_PATH)
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
            request.getSession();
            User user = new User();
            user.setLogin(name);
            user.setPassword(password);
            if (userService.existsUser(user)) {
                if (user.getPassword().equals(userService.find(user).getPassword())) {
                    request.getSession().setAttribute("username", user.getLogin());
                    return new ResponseEntity(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Wrong password", HttpStatus.NOT_ACCEPTABLE);
                }
            } else {
                return new ResponseEntity<>("User with that login doesn't exist", HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
