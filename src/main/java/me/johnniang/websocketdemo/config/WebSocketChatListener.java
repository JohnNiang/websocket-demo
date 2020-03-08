package me.johnniang.websocketdemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

//@Component
public class WebSocketChatListener {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent connectedEvent) {
        log.info("Received a new websocket connection. Message: [{}]", connectedEvent);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent disconnectEvent) {
        log.info("Someone Disconnected. Message: [{}]", disconnectEvent);
    }
}
