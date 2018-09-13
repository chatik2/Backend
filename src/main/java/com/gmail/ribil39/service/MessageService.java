package com.gmail.ribil39.service;

import com.gmail.ribil39.model.Message;
import com.gmail.ribil39.model.MessageDTO;
import com.gmail.ribil39.model.User;
import com.gmail.ribil39.repository.MessageRepository;
import com.gmail.ribil39.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    public static final long HOUR = 3600 * 1000;

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

    public List<MessageDTO> getLastMessages(Long id){
        long uid = id;
        User user = userRepository.findById(uid);
        List<Message> messageResult = messageRepository.findAll();
        List<Message> lastMessageResult = new ArrayList<>();
        for (Message m : messageResult) {
            if(user.getLastActivityDate().before(m.getDate())){
                lastMessageResult.add(m);
            }
        }

        List<MessageDTO> lastMessagesDTOList = new ArrayList<>();
        for (Message m : lastMessageResult) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setId(m.getId());
            messageDTO.setText(m.getText());
            messageDTO.setDate(m.getDate());
            messageDTO.setSecondUserId(m.getAuthor().getSecondId());
            lastMessagesDTOList.add(messageDTO);
        }
        return lastMessagesDTOList;
    }

    public void changeNameMessage(User user, String newName){
        Message message = new Message();
        message.setDate(new Date(new Date().getTime() + 3 * HOUR));
        message.setAuthor(user);
        message.setText("User " + user.getName() + " changed name to " + newName);
        messageRepository.save(message);
    }

    public Message createMessage(long userId, String text) {

        Message message = new Message();
        message.setText(text);

        message.setDate(new Date(new Date().getTime() + 3 * HOUR));
        message.setAuthor(userRepository.findById(userId));

        messageRepository.save(message);

        return message;
    }
}
