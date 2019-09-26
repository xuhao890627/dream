package com.st.dream.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.st.dream.utils.MQConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;



// #匹配一个或者多个
// * 匹配一个
public class TopicSend {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = MQConnection.getConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_exchange_topic";
        channel.exchangeDeclare(exchangeName, "topic");

        String msg = "商品...";

        String routingKey = "goods.delete";

        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());

        System.out.println("msg send : " + msg);

        channel.close();

        connection.close();
    }
}
