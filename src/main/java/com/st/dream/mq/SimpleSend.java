package com.st.dream.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.st.dream.utils.MQConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SimpleSend {

    //耦合高， 生产者消费者一一对应

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = MQConnection.getConnection();

        Channel channel = connection.createChannel();

        String queueName = "test_simple_queue";
        channel.queueDeclare(queueName, false, false, false, null);

        String msg = "hello simple aaaa";

        channel.basicPublish("", queueName, null, msg.getBytes());

        System.out.println("send scuss");

        channel.close();

        connection.close();

    }
}
