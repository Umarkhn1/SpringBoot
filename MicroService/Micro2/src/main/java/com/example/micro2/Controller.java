package com.example.micro2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping
    public String hello() {
        return "Second Microservice";
    }

    @PostMapping("/message/{id}")
    public ResponseEntity<MessageDTO> hello(@PathVariable Long id) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(id);
        messageDTO.setBody("Hello Microservice2");
        return ResponseEntity.ok(messageDTO);
    }

}

