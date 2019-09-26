package com.st.dream.mq;

import com.rabbitmq.client.*;
import com.st.dream.utils.MQConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class WorkReceive1 {
    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = MQConnection.getConnection();

        Channel channel = connection.createChannel();


        String queueName = "test_work_queue";
        channel.queueDeclare(queueName, false, false, false, null);


        //
        channel.basicQos(1);

        Consumer consumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);

                String msg = new String(body, "utf-8");

                System.out.println("[1] receive msg: " + msg);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[1] done.");
                    //
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        // 公平分发的时候，必须关闭自动应答ack
        boolean autoAck = true;

        channel.basicConsume(queueName, false, consumer);
    }
}
