package me.johnniang.websocketdemo.config;

import me.johnniang.websocketdemo.listener.WebSocketConnectHandler;
import me.johnniang.websocketdemo.listener.WebSocketDisconnectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import javax.websocket.Session;

/**
 * WebSocket handler configuration.
 *
 * @author johnniang
 */
@Configuration
public class WebSocketHandlerConfiguration<S extends Session> {

    @Bean
    public WebSocketConnectHandler<S> webSocketConnectHandler(SimpMessagingTemplate messagingTemplate) {
        return new WebSocketConnectHandler<>(messagingTemplate);
    }

    @Bean
    public WebSocketDisconnectHandler<S> webSocketDisconnectHandler(SimpMessagingTemplate messageTemplate) {
        return new WebSocketDisconnectHandler<>(messageTemplate);
    }
}
