package com.st.dream.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.st.dream.utils.MQConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RoutingSend {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = MQConnection.getConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_exchange_direct";
        channel.exchangeDeclare(exchangeName, "direct");

        String msg = "hello direct";

        String routingKey = "info";

        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());

        System.out.println("msg send : " + msg);

        channel.close();

        connection.close();
    }
}
