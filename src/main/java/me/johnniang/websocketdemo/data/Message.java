package me.johnniang.websocketdemo.data;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

/**
 * Definition of message.
 *
 * @author johnniang
 */
public class Message<T> {

    /**
     * 消息 ID
     */
    private final String id;

    /**
     * 消息类型
     */
    private final MessageType type;

    /**
     * 消息数据
     */
    private final T data;

    public Message(@NonNull String id, @NonNull MessageType type, @NonNull T data) {
        Assert.hasText(id, "Message id must not be blank");
        Assert.notNull(type, "Message type must not be null");
        Assert.notNull(data, "Message data must not be null");

        this.id = id;
        this.type = type;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public MessageType getType() {
        return type;
    }

    @NonNull
    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Message{" +
            "id='" + id + '\'' +
            ", type=" + type +
            ", data=" + data +
            '}';
    }

    /**
     * New order data.
     */
    public static class NewOrderData {

        private String orderNumber;

        private String senderAddress;

        private String receiverAddress;

        private OrderBizType bizType;

        private OrderType type;

        private LocalDateTime createTime;

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getSenderAddress() {
            return senderAddress;
        }

        public void setSenderAddress(String senderAddress) {
            this.senderAddress = senderAddress;
        }

        public String getReceiverAddress() {
            return receiverAddress;
        }

        public void setReceiverAddress(String receiverAddress) {
            this.receiverAddress = receiverAddress;
        }

        public OrderBizType getBizType() {
            return bizType;
        }

        public void setBizType(OrderBizType bizType) {
            this.bizType = bizType;
        }

        public OrderType getType() {
            return type;
        }

        public void setType(OrderType type) {
            this.type = type;
        }

        public LocalDateTime getCreateTime() {
            return createTime;
        }

        public void setCreateTime(LocalDateTime createTime) {
            this.createTime = createTime;
        }

        @Override
        public String toString() {
            return "NewOrderData{" +
                "orderNumber='" + orderNumber + '\'' +
                ", senderAddress='" + senderAddress + '\'' +
                ", receiverAddress='" + receiverAddress + '\'' +
                ", bizType=" + bizType +
                ", type=" + type +
                ", createTime=" + createTime +
                '}';
        }
    }

    public static class NewDriverApplicationData {
        private int applicationId;
        private String name;
        private LocalDateTime applyTime;
        private String residentCity;
    }
}
