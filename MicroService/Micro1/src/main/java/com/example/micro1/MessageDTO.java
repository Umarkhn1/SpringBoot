package com.example.micro1;

public class MessageDTO extends MessageFrom2DTO {

    private Long createdAt;

    public MessageDTO(Long id, String body, Long createdAt) {
        super(id, body);
        this.createdAt = createdAt;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}

