package com.st.dream.mq;

import com.rabbitmq.client.*;
import com.st.dream.utils.MQConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RoutingReceive1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = MQConnection.getConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_exchange_direct";

        String queueName = "test_queue_direct_1";

        channel.queueDeclare(queueName, false, false, false, null);

        channel.basicQos(1);

        channel.queueBind(queueName, exchangeName, "error");

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
        boolean autoAck = true; //消息从队列中给到消费者，就会从内存中删除， 如果杀死正在执行的消费者，就会丢失正在处理的消息

        //如果rabbitMQ挂了 消息仍然会丢失

        channel.basicConsume(queueName, false, consumer);
    }
}
