package com.st.dream.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.st.dream.utils.MQConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FanoutSend {

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = MQConnection.getConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_exchange_fanout";

        //声明交换机
        channel.exchangeDeclare(exchangeName, "fanout");  //fanout 不处理路由键

        //发送消息

        String msg = "hello ps";

        channel.basicPublish(exchangeName, "", null, msg.getBytes());

        System.out.println("msg send : " + msg);

        channel.close();
        connection.close();
    }
}
