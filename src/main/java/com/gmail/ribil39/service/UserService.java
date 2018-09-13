package com.gmail.ribil39.service;

import com.gmail.ribil39.model.User;
import com.gmail.ribil39.model.UserDTO;
import com.gmail.ribil39.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class UserService {

    public static final long HOUR = 3600 * 1000;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageService messageService;

    public User createNewUser(){

        List<User> userResult = userRepository.findAll();

        User user = new User();

        Random random = new Random();
        boolean uniqueName = false;
        while (!uniqueName) {
            String randomName = "anonim" + String.valueOf(random.nextInt(89999) + 10000);

            if (userResult.size() == 0) {
                Integer randomSecondId = random.nextInt(89999) + 10000;
                user.setSecondId(new Long(randomSecondId));
                user.setName(randomName);

                user.setLastActivityDate(new Date(new Date().getTime() + 3 * HOUR));
                user.setLastRequestDate(new Date(new Date().getTime() + 3 * HOUR));
                userRepository.save(user);
                return user;
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

        return user;
    }

    public boolean ifUserExists(long userId){
        List<User> userResult = userRepository.findAll();
        boolean userExists = false;
        for (User u : userResult) {
            if (u.getId().equals(userId)) {
                userExists = true;
            }
        }
        if (!userExists) {
            userExists = false;
        }
        return userExists;
    }

    public User findUserById(long uid){
        User user = userRepository.findById(uid);
        return user;
    }

    public User changeUserName(long userId, String userName) {
        User user = userRepository.findById(userId);

        messageService.changeNameMessage(user, userName);

        user.setName(userName);
        user.setLastActivityDate(new Date(new Date().getTime() + 3 * HOUR));
        userRepository.save(user);
        return user;
    }

    public List<UserDTO> getDTOUsers(){
        List<User> userResult = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User u : userResult) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(u.getSecondId());
            userDTO.setName(u.getName());
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    public List<UserDTO> getLastRequestUsers(Long id){
        long uid = id;
        User user = userRepository.findById(uid);
        List<User> userResult = userRepository.findAll();
        List<User> lastActivityUserResult = new ArrayList<>();
        for (User u : userResult) {

            if(user.getLastRequestDate().before(u.getLastRequestDate()) ||
                    user.getLastRequestDate().before(u.getLastActivityDate())){
                lastActivityUserResult.add(u);
            }
        }

        List<UserDTO> lastActivityUserDTOList = new ArrayList<>();
        for (User u : lastActivityUserResult) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(u.getSecondId());
            userDTO.setName(u.getName());
            lastActivityUserDTOList.add(userDTO);
        }

        return lastActivityUserDTOList;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
