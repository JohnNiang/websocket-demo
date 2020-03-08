package me.johnniang.websocketdemo.controller;

import me.johnniang.websocketdemo.domain.WebSocketChatMessage;
import me.johnniang.websocketdemo.domain.WebSocketOutChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class WebSocketChatController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send")
    public void sendMessage(@Payload WebSocketChatMessage message,
//                            @Header("simpleSessionId") String sessionId,
                            Principal user) {
        WebSocketOutChatMessage outChatMessage = new WebSocketOutChatMessage();
        outChatMessage.setFrom(message.getFrom());
        outChatMessage.setContent(message.getContent());
        outChatMessage.setTimestamp(LocalDateTime.now());

        messagingTemplate.convertAndSendToUser(message.getTo(), "/users/queue/specific-user", outChatMessage);
    }

    @MessageMapping("/message")
    @SendToUser(value = "/queue/reply")
    public String processMessageFromClient(@Payload String message, Principal user) {
        log.info("Got message: [{}] of user: [{}]", message, user);
        throw new RuntimeException("I made an exception.");
//        return "You got a message: " + message;
    }

    @MessageExceptionHandler
    @SendToUser(value = "/queue/errors")
    public String handleException(Throwable exception) {
        log.error("Caught an exception.", exception);
        return exception.getMessage();
    }

}
