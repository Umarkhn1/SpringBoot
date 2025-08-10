package com.example.micro1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@RestController
public class Controller {
    private final MService service;

    public Controller(MService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<String> hello() {
        String result = service.hello();
        return ResponseEntity.ok(result);
    }
    @GetMapping("/message")
    public ResponseEntity<MessageDTO> message(Long id) {
        MessageDTO messageDTO = service.message(id);
        return ResponseEntity.ok(messageDTO);
    }
}


