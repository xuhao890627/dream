package com.st.dream.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.st.dream.utils.MQConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConfirmSend1 {

    // Confirm模式最大的好处是这种模式是异步的

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = MQConnection.getConnection();

        Channel channel = connection.createChannel();

        String queueName = "test_queue_confirm1";

        channel.queueDeclare(queueName, false, false, false, null);

        //开启confirm 模式

        // 普通发一条 waitForConfirm
        // 批量 发一批 waitForConfirm
        // 异步confirm模式， 提供回调方法

        //如果队列已经设置成txselect模式， 就无法设置成confirm模式
        channel.confirmSelect();

        String msg = "hello tx messag sse";


        //批量
//        for (int i= 0; i<9; i++) {
//            channel.basicPublish("", queueName, null, msg.getBytes());
//        }

        channel.basicPublish("", queueName, null, msg.getBytes());

        if (!channel.waitForConfirms()) {
            System.out.println("message send failed....");
        } else {
            System.out.println("send message [confirm]. "+ msg);
        }

        channel.close();
        connection.close();
    }
}
