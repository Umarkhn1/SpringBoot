package WebSocket.Java.rest;

import WebSocket.Java.Component.SendDate;
import WebSocket.Java.Entity.Message;
import WebSocket.Java.Repository.MessageRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final SendDate sendDate;
    private final MessageRepository messageRepository;

    public HomeController(SendDate sendDate, MessageRepository messageRepository) {
        this.sendDate = sendDate;
        this.messageRepository = messageRepository;
    }

    @GetMapping
    public String home(){
        return "home";
    }
    @GetMapping("/message")
    public String message(){
        return "message";
    }
    @MessageMapping("/type")
    public void type(String message){
        Message msg = new Message(message);
        messageRepository.save(msg);
        sendDate.type();
    }

    @MessageMapping("/online")
    public void online(){
        sendDate.online();
    }
}
