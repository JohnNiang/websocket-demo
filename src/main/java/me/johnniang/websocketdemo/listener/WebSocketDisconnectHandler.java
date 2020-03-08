package me.johnniang.websocketdemo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * WebSocket disconnect handler.
 *
 * @author johnniang
 */
public class WebSocketDisconnectHandler<S> implements ApplicationListener<SessionDisconnectEvent> {


    private final Logger log = LoggerFactory.getLogger(getClass());

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketDisconnectHandler(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent disconnectEvent) {
        log.info("Someone Disconnected. Message: [{}]", disconnectEvent);
    }
}
