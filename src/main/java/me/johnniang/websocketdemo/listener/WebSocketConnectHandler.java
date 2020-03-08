package me.johnniang.websocketdemo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.security.Principal;

/**
 * WebSocket connect handler.
 */
public class WebSocketConnectHandler<S> implements ApplicationListener<SessionConnectedEvent> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketConnectHandler(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void onApplicationEvent(SessionConnectedEvent connectedEvent) {
        log.info("Received a new websocket connection. Message: [{}]", connectedEvent);
        MessageHeaders headers = connectedEvent.getMessage().getHeaders();
        Principal user = SimpMessageHeaderAccessor.getUser(headers);
        log.info("Got user: [{}]", user);
    }
}
