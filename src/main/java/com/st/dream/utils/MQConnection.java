package com.st.dream.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MQConnection {

    public static Connection getConnection() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("10.201.10.17");

        factory.setPort(5672);

        factory.setVirtualHost("/fordream");

        factory.setUsername("dream");

        factory.setPassword("123456");

        return factory.newConnection();
    }
}
