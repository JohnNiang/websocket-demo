package me.johnniang.websocketdemo.listener;

import me.johnniang.websocketdemo.data.Message;
import me.johnniang.websocketdemo.data.MessageType;
import me.johnniang.websocketdemo.data.OrderBizType;
import me.johnniang.websocketdemo.data.OrderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
        log.info("Got token from header: [{}]", headers);
        Principal user = SimpMessageHeaderAccessor.getUser(headers);
        log.info("Got user: [{}]", user);

        Thread notificationSender = new Thread(new NotificationSender(connectedEvent.getUser()));
        notificationSender.setName("notification-sender");
        notificationSender.start();
    }

    public class NotificationSender implements Runnable {

        private final Principal user;

        public NotificationSender(Principal user) {
            this.user = user;
        }

        @Override
        public void run() {
            Random random = new Random();
            try {
                while (true) {
                    // build message
                    Message.NewOrderData orderData = new Message.NewOrderData();
                    orderData.setOrderNumber(UUID.randomUUID().toString());
                    orderData.setBizType(OrderBizType.FTL);
                    orderData.setType(OrderType.NORMAL_ORDER);
                    orderData.setCreateTime(LocalDateTime.now());
                    orderData.setSenderAddress("重庆市沙坪垻");
                    orderData.setReceiverAddress("成都市成华区");
                    orderData.setUserId(user.getName());

                    Message<Message.NewOrderData> newOrderDataMessage = new Message<>(UUID.randomUUID().toString(), MessageType.NEW_ORDER, orderData);
                    log.debug("Trying to send a new order data message: [{}] for user: [{}]", newOrderDataMessage, user);

//                    messagingTemplate.convertAndSend("/topic/notification", newOrderDataMessage);
                    messagingTemplate.convertAndSendToUser(user.getName(), "/queue/notice", newOrderDataMessage);

                    TimeUnit.SECONDS.sleep(1 + random.nextInt(5));
                }
            } catch (InterruptedException e) {
                log.error("Interrupted!", e);
            }
        }
    }
}
