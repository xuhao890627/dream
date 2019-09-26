package com.st.dream.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.st.dream.utils.MQConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TransactionSend {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = MQConnection.getConnection();

        Channel channel = connection.createChannel();

        String queueName = "test_queue_tx";

        channel.queueDeclare(queueName, false, false, false, null);

        String msg = "hello tx messag sse";

        try {
            channel.txSelect();

            channel.basicPublish("", queueName, null, msg.getBytes());

//            int xx = 1/0;

            channel.txCommit();
            System.out.println("send message [tx]. "+ msg);
        } catch (Exception e) {
            channel.txRollback();
            System.out.println("send message rollback.");
        } finally {

        }

        channel.close();
        connection.close();

    }
}
