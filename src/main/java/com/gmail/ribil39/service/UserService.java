package com.gmail.ribil39.service;

import com.gmail.ribil39.model.User;
import com.gmail.ribil39.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

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
}
