package me.johnniang.websocketdemo;

import me.johnniang.websocketdemo.config.CustomStompSessionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebSocketDemoApplicationTests {

    @Autowired
    WebSocketStompClient stompClient;

    @LocalServerPort
    int port;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(stompClient);
    }

    @Test
    void connectFailure() {
        Assertions.assertThrows(Exception.class, () -> {
            final CustomStompSessionHandler stompSessionHandler = new CustomStompSessionHandler();
            final StompHeaders stompHeaders = new StompHeaders();
            stompHeaders.add("token", "opentest");
            final String url = "http://127.0.0.1:" + port + "/online_chat";
            StompSession stompSession = stompClient.connect(new URI(url), null, stompHeaders, stompSessionHandler).get();
            Assertions.assertNotNull(stompSession);
        });
    }

    @Test
    void connectSuccess() throws URISyntaxException, ExecutionException, InterruptedException {
        final CustomStompSessionHandler stompSessionHandler = new CustomStompSessionHandler();
        final StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.add("token", "openwebsocket");
        final String url = "http://127.0.0.1:" + port + "/online_chat";
        StompSession stompSession = stompClient.connect(new URI(url), null, stompHeaders, stompSessionHandler).get();
        Assertions.assertNotNull(stompSession);
    }

}
