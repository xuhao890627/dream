package com.st.dream.mq;

import com.rabbitmq.client.*;
import com.st.dream.utils.MQConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SimpleReceive {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = MQConnection.getConnection();
        Channel channel = connection.createChannel();

        String queueName = "test_simple_queue";
        channel.queueDeclare(queueName, false, false, false, null);

        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);

                String msgString = new String(body, "utf-8");

                System.out.println("received..." + msgString);
            }
        };

        channel.basicConsume(queueName, true, consumer);
    }
}
