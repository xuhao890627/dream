package com.st.dream.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.st.dream.utils.MQConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class WorkSend {

    //轮训分发， 接受者拿到的都一样
    //公平分发

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = MQConnection.getConnection();

        Channel channel = connection.createChannel();

        String queueName = "test_work_queue";

        channel.queueDeclare(queueName, false, false, false, null);


        // 每个消费者发送确认消息之前 消息队列不发送下一个消息到消费者 一次只处理一个信息
        int prefetCount = 1;
        channel.basicQos(prefetCount);

        for (int i=0; i< 49; i++) {
            String msg = "hello " + i;
            channel.basicPublish("", queueName, null, msg.getBytes());
            System.out.println("message send : "+ msg );
            try {
                Thread.sleep(1* 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        channel.close();

        connection.close();
    }
}
