package com.st.dream.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.st.dream.utils.MQConnection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

public class ConfirmSendAsync {

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = MQConnection.getConnection();

        Channel channel = connection.createChannel();

        String queueName = "test_queue_confirm_async";

        channel.queueDeclare(queueName, false, false, false, null);

        channel.confirmSelect();

        SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("----handleAck----multiples");
                    confirmSet.headSet(deliveryTag+1).clear();
                } else {
                    System.out.println("----handleAck----multiples false");
                    confirmSet.remove(deliveryTag);
                }
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("----handleNack----multiples");
                    confirmSet.headSet(deliveryTag+1).clear();
                } else {
                    System.out.println("----handleNack----multiples false");
                    confirmSet.remove(deliveryTag);
                }
            }
        });

        String msg = "sssssss";

        while (true) {
            long seqno = channel.getNextPublishSeqNo();
            channel.basicPublish("", queueName, null, msg.getBytes());
        }



    }
}
