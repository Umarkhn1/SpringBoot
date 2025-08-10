package com.example.micro1;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class MService implements Repository {
    @LoadBalanced
    private final RestTemplate restTemplate;

    public MService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    public String hello() {
        String response = restTemplate.getForObject("http://Micro2", String.class);
        return "First Microservice "+response;
    }
    @Override
    public MessageDTO message(Long id) {
        MessageFrom2DTO response = restTemplate.postForObject("http://Micro2/message/"+id,null, MessageFrom2DTO.class);
        if(response == null) {
            return null;
        }
        return new MessageDTO(id,response.getBody(), new Date().getTime());
    }

}
