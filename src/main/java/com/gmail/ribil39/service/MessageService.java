package com.gmail.ribil39.service;

import com.gmail.ribil39.model.Message;
import com.gmail.ribil39.model.User;
import com.gmail.ribil39.repository.MessageRepository;
import com.gmail.ribil39.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    public List<MessageDTO> getDTOMessages(){
        List<Message> messageResult = messageRepository.findAll();
        List<MessageDTO> messageDTOList = new ArrayList<>();
        for (Message m : messageResult) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setId(m.getId());
            messageDTO.setText(m.getText());
            messageDTO.setDate(m.getDate());
            messageDTO.setSecondUserId(m.getAuthor().getSecondId());
            messageDTOList.add(messageDTO);
        }
        return messageDTOList;
    }
}
