package com.gmail.ribil39.controller;

import com.gmail.ribil39.model.Message;
import com.gmail.ribil39.model.User;
import com.gmail.ribil39.repository.MessageRepository;
import com.gmail.ribil39.repository.UserRepository;
import com.gmail.ribil39.service.MessageDTO;
import com.gmail.ribil39.service.MessageService;
import com.gmail.ribil39.service.UserDTO;
import com.gmail.ribil39.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@CrossOrigin
@RestController
public class MainController {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    public static final long HOUR = 3600*1000; // in milli-seconds

    @RequestMapping(value = "/new_user", produces={"application/json; charset=UTF-8"})
    ResponseEntity<User> getNewUser(
    ) {

        List<User> userResult = userRepository.findAll();

        User user = new User();

        Random random = new Random();
        boolean uniqueName = false;
        while (!uniqueName) {
            String randomName = "anonim" + String.valueOf(random.nextInt(89999) + 10000);

            if(userResult.size() == 0){
                Integer randomSecondId = random.nextInt(89999) + 10000;
                user.setSecondId(new Long(randomSecondId));
                user.setName(randomName);

                user.setLastActivityDate(new Date(new Date().getTime() + 3 * HOUR));
                user.setLastRequestDate(new Date(new Date().getTime() + 3 * HOUR));
                userRepository.save(user);
                return new ResponseEntity<>(user, HttpStatus.OK);
            }

            for (User u : userResult) {
                if (u.getName().equals(randomName)) {
                    uniqueName = false;
                } else {
                    uniqueName = true;
                }
            }
            user.setName(randomName);
        }

        boolean uniqueSecondId = false;
        while (!uniqueSecondId) {
            Integer randomSecondId = random.nextInt(89999) + 10000;
            for (User u : userResult) {
                if (u.getSecondId().equals(randomSecondId)) {
                    uniqueSecondId = false;
                } else {
                    uniqueSecondId = true;
                }
            }
            user.setSecondId(new Long(randomSecondId));
            user.setLastActivityDate(new Date(new Date().getTime() + 3 * HOUR));
            user.setLastRequestDate(new Date(new Date().getTime() + 3 * HOUR));
        }

        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/new_name", produces={"application/json; charset=UTF-8"})
    ResponseEntity<User> setUserName(
            @RequestHeader(value = "uid", required = false) long userId,
            //@RequestParam(value = "uid", required = false) long userId,
            @RequestHeader(value = "type", required = false) String type,
            @RequestBody String userName
    ) {

        List<User> userResult = userRepository.findAll();
        boolean userExists = false;
        for (User u : userResult) {
            if (u.getId().equals(userId)) {
                userExists = true;
            }
        }
        if (!userExists) {
            return new ResponseEntity<>(/*responseHeaders,*/ HttpStatus.OK);
        }

        User user = userRepository.findById(userId);

        Message message = new Message();
        message.setDate(new Date(new Date().getTime() + 3 * HOUR));
        message.setAuthor(user);
        message.setText("User " + user.getName() + " changed name to " + userName);
        messageRepository.save(message);

        user.setName(userName);
        user.setLastActivityDate(new Date(new Date().getTime() + 3 * HOUR));
        userRepository.save(user);

        return new ResponseEntity<>(user, /*responseHeaders,*/ HttpStatus.OK);

    }

    @RequestMapping(value = "/users", produces={"application/json; charset=UTF-8"})
    ResponseEntity<List<UserDTO>> getUsers(
            @RequestHeader(value = "uid", required = false) Long userId,
            //@RequestParam(value = "uid", required = false) Long userId,
            //@RequestParam(value = "type", required = false) String type
            @RequestHeader(value = "type", required = false) String type
    ) {

        List<User> userResult = userRepository.findAll();

        boolean userExists = false;
        for (User u : userResult) {
            if (u.getId().equals(userId)) {
                userExists = true;
            }
        }
        if (!userExists) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        long uid = userId;
        User user = userRepository.findById(uid);


        if(type != null && type.equals("mobile")){
            List<UserDTO> userDTOResult = userService.getLastRequestUsers(userId);
           /* if(userDTOResult.size()==0){
                MainController.returnNull();
            }*/
            user.setLastRequestDate(new Date(new Date().getTime() + 3 * HOUR));
            userRepository.save(user);
            return new ResponseEntity<>(userDTOResult, HttpStatus.OK);
        }

        List<UserDTO> userDTOResult = userService.getDTOUsers();

        user.setLastRequestDate(new Date(new Date().getTime() + 3 * HOUR));
        userRepository.save(user);

        return new ResponseEntity<>(userDTOResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/msgs", produces={"application/json; charset=UTF-8"})
    ResponseEntity<List<MessageDTO>> getMessages(
           @RequestHeader(value = "uid", required = false) Long userId,
            //@RequestParam(value = "uid", required = false) Long userId,
            @RequestHeader(value = "type", required = false) String type
    ) {

        List<User> userResult = userRepository.findAll();
        boolean userExists = false;
        for (User u : userResult) {
            if (u.getId().equals(userId)) {
                userExists = true;
            }
        }
        if (!userExists) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        long uid = userId;
        User user = userRepository.findById(uid);

        if(type != null && type.equals("mobile")){
            List<MessageDTO> messageDTOResult = messageService.getLastMessages(userId);
            user.setLastRequestDate(new Date(new Date().getTime() + 3 * HOUR));
            userRepository.save(user);
            return new ResponseEntity<>(messageDTOResult, HttpStatus.OK);
        }

        List<MessageDTO> messageDTOList = messageService.getDTOMessages();

        user.setLastRequestDate(new Date(new Date().getTime() + 3 * HOUR));
        userRepository.save(user);

        return new ResponseEntity<>(messageDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/new_msg", produces={"application/json; charset=UTF-8"})
    ResponseEntity<Message> saveMessage(
            @RequestHeader(value = "uid", required = false) long userId,
           // @RequestHeader(value = "type", required = false) String type,
            @RequestBody String text
           //,@RequestParam(value = "uid", required = false) long userId
    ) {

        List<User> userResult = userRepository.findAll();
        boolean userExists = false;
        for (User u : userResult) {
            if (u.getId() == userId) {
                userExists = true;
            }
        }
        if (!userExists) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        Message message = new Message();
        message.setText(text);

        message.setDate(new Date(new Date().getTime() + 3 * HOUR));
        message.setAuthor(userRepository.findById(userId));

        messageRepository.save(message);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

   /* @RequestMapping(value = "/returnnull", produces={"application/json; charset=UTF-8"})
    static ResponseEntity<String> returnNull(){

        return new ResponseEntity<>("gg", HttpStatus.OK);
    }*/

    @RequestMapping(value = "/allusers", produces={"application/json; charset=UTF-8"})
    ResponseEntity<List<UserDTO>> getAllUsers(
            //@RequestHeader(value = "uid", required = false) Long userId,
            @RequestParam(value = "uid", required = false) Long userId,
            @RequestHeader(value = "type", required = false) String type
    ) {

        List<User> userResult = userRepository.findAll();

        boolean userExists = false;
        for (User u : userResult) {
            if (u.getId().equals(userId)) {
                userExists = true;
            }
        }
        if (!userExists) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        long uid = userId;
        User user = userRepository.findById(uid);


        if(type != null && type.equals("mobile")){
            List<UserDTO> userDTOResult = userService.getLastRequestUsers(userId);
            user.setLastRequestDate(new Date(new Date().getTime() + 3 * HOUR));
            userRepository.save(user);
            return new ResponseEntity<>(userDTOResult, HttpStatus.OK);
        }

        List<UserDTO> userDTOResult = userService.getDTOUsers();

        user.setLastRequestDate(new Date(new Date().getTime() + 3 * HOUR));
        userRepository.save(user);

        return new ResponseEntity<>(userDTOResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/allmsgs", produces={"application/json; charset=UTF-8"})
    ResponseEntity<List<MessageDTO>> getAllMessages(
            //@RequestHeader(value = "uid", required = false) Long userId,
            @RequestParam(value = "uid", required = false) Long userId,
            @RequestHeader(value = "type", required = false) String type
    ) {

        List<User> userResult = userRepository.findAll();
        boolean userExists = false;
        for (User u : userResult) {
            if (u.getId().equals(userId)) {
                userExists = true;
            }
        }
        if (!userExists) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        long uid = userId;
        User user = userRepository.findById(uid);


        if(type != null && type.equals("mobile")){
            List<MessageDTO> messageDTOResult = messageService.getLastMessages(userId);
            user.setLastRequestDate(new Date(new Date().getTime() + 3 * HOUR));
            userRepository.save(user);
            return new ResponseEntity<>(messageDTOResult, HttpStatus.OK);
        }

        List<MessageDTO> messageDTOList = messageService.getDTOMessages();

        user.setLastRequestDate(new Date(new Date().getTime() + 3 * HOUR));
        userRepository.save(user);

        return new ResponseEntity<>(messageDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/new", produces={"application/json; charset=UTF-8"})
    ResponseEntity<Message> newMessage(

            @RequestParam(value = "uid", required = false) long userId,
            @RequestParam(value = "text", required = false) String text
    ) {

        List<User> userResult = userRepository.findAll();
        boolean userExists = false;
        for (User u : userResult) {
            if (u.getId() == userId) {
                userExists = true;
            }
        }
        if (!userExists) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        Message message = new Message();
        message.setText(text);

        message.setDate(new Date(new Date().getTime() + 3 * HOUR));
        message.setAuthor(userRepository.findById(userId));

        messageRepository.save(message);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }


}
