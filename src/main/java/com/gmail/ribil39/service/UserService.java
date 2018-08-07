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
}
