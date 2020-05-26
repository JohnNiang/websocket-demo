package me.johnniang.websocketdemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Collections;
import java.util.Objects;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketChatConfiguration implements WebSocketMessageBrokerConfigurer {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    public WebSocketClient webSocketClient() {
        return new SockJsClient(Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient())));
    }

    @Bean
    public WebSocketStompClient stompClient() {
        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(webSocketClient());
        webSocketStompClient.setMessageConverter(new StringMessageConverter());
        return webSocketStompClient;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/online_chat").setAllowedOrigins("*");
        registry.addEndpoint("/online_chat").setAllowedOrigins("*")
            .withSockJS();
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app")
            .setPreservePublishOrder(true)
            .enableStompBrokerRelay("/topic", "/queue")
            .setSystemHeartbeatSendInterval(20000)
            .setSystemHeartbeatReceiveInterval(10000)
            .setRelayHost("xxx.yyy.zzz")
            .setRelayPort(61613)
            .setAutoStartup(true)
            .setClientLogin("giant")
            .setClientPasscode("xxx")
            .setSystemLogin("giant")
            .setSystemPasscode("xxx")
            .setVirtualHost("giant_vhost");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new GiantChannelInterceptor());
    }

    public static class GiantChannelInterceptor implements ChannelInterceptor {

        private final Logger log = LoggerFactory.getLogger(getClass());

        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            log.debug("Giant channel interceptor");
            StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
            if (accessor != null && Objects.equals(accessor.getCommand(), StompCommand.CONNECT)) {
                // get token
                String token = accessor.getFirstNativeHeader("token");
                if (Objects.equals(token, "openwebsocket")) {
                    String username = accessor.getFirstNativeHeader("username");
                    User user = new User(username);
                    log.debug("Created user: [{}] with token: [{}]", user, token);
                    accessor.setUser(user);
                } else {
                    throw new RuntimeException("Unauthorized access");
                }
            }
            return message;
        }
    }

    public static class User implements Principal {

        private final String name;

        public User(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean implies(Subject subject) {
            return false;
        }

        @Override
        public String toString() {
            return "User{" +
                "name='" + name + '\'' +
                '}';
        }
    }
}
