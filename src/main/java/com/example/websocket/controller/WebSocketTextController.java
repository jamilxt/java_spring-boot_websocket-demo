package com.example.websocket.controller;

import com.example.websocket.dto.TextMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketTextController {
  private final SimpMessagingTemplate simpMessagingTemplate;

  public WebSocketTextController(SimpMessagingTemplate simpMessagingTemplate) {
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  @PostMapping("/send")
  public ResponseEntity<Void> sendMessage(@RequestBody TextMessageDTO textMessageDTO) {
    simpMessagingTemplate.convertAndSend("/topic/message", textMessageDTO);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @MessageMapping("/sendMessage")
  public void receiveMessage(@Payload TextMessageDTO textMessageDTO) {
    // receive message from client
    System.out.println(textMessageDTO);
  }


  @SendTo("/topic/message")
  public TextMessageDTO broadcastMessage(@Payload TextMessageDTO textMessageDTO) {
    return textMessageDTO;
  }
}
