package com.st.dream.mq;

import com.rabbitmq.client.*;
import com.st.dream.utils.MQConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConfirmReceiveAsync {

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = MQConnection.getConnection();

        Channel channel = connection.createChannel();

        String queueName = "test_queue_confirm_async";

        channel.queueDeclare(queueName, false, false, false, null);

        channel.basicConsume(queueName, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("receive[confirm] msg :" + new String(body, "utf-8"));
            }
        });
    }
}
