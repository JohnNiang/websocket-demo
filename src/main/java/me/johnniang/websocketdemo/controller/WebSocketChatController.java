package me.johnniang.websocketdemo.controller;

import me.johnniang.websocketdemo.data.Message;
import me.johnniang.websocketdemo.data.MessageType;
import me.johnniang.websocketdemo.data.OrderBizType;
import me.johnniang.websocketdemo.data.OrderType;
import me.johnniang.websocketdemo.domain.WebSocketChatMessage;
import me.johnniang.websocketdemo.domain.WebSocketOutChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class WebSocketChatController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/message")
    @SendToUser("/queue/notice")
    public String processMessageFromClient(@Payload String message, Principal user) {
        log.info("Got message: [{}] of user: [{}]", message, user);
//        throw new RuntimeException("I made an exception.");
        return "You got a message: " + message;
    }

    @MessageMapping("/reply")
    @SendTo(value = "/topic/reply")
    public Message<Message.NewOrderData> processTopicMessageFromClient(@Payload String message, @Header(value = "token", required = false) String token, Principal user) {
        log.info("Got message: [{}] of user: [{}]", message, user);
        log.info("Got token: [{}]", token);

        // build message
        Message.NewOrderData orderData = new Message.NewOrderData();
        orderData.setOrderNumber(UUID.randomUUID().toString());
        orderData.setBizType(OrderBizType.FTL);
        orderData.setType(OrderType.NORMAL_ORDER);
        orderData.setCreateTime(LocalDateTime.now());
        orderData.setSenderAddress("重庆市沙坪垻");
        orderData.setReceiverAddress("成都市成华区");

        Message<Message.NewOrderData> newOrderDataMessage = new Message<>(UUID.randomUUID().toString(), MessageType.NEW_ORDER, orderData);

        log.debug("Trying to send a new order data message: [{}]", newOrderDataMessage);

        return newOrderDataMessage;
    }

    @MessageExceptionHandler
    @SendTo(value = "/topic/errors")
    public String handleTopicException(Throwable exception) {
        log.error("Caught an exception.", exception);
        return exception.getMessage();
    }

    @MessageExceptionHandler
    @SendToUser(value = "/queue/errors", broadcast = false)
    public String handleException(Throwable exception) {
        log.error("Caught an exception.", exception);
        return exception.getMessage();
    }

}
