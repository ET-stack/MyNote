package com.demo.rabbitmq.Rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Yi
 * @date 2020/6/8 16:38
 */
public class RabbitProducer {
    private static final String exchange_name = "exchange_demo";
    private static final String routing_key = "routingkey_demo";
    private static final String queue_name = "queue_demo";
    private static final String ip_address = "localhost";
    private static final int port = 5672;

    public static void main(String[] args) throws IOException, TimeoutException,InterruptedException{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ip_address);
        factory.setPort(port);
        factory.setUsername("admin");
        factory.setPassword("admin");
        com.rabbitmq.client.Connection connection = factory.newConnection();
        //创建连接
        Channel channel = connection.createChannel();
        //创建信道
        channel.exchangeDeclare(exchange_name,"direct",true,false,null);
        //创建一个持久化的 非排他的 非自  动删除的队列
        channel.queueBind(queue_name,exchange_name,routing_key);
        //发一条持久化的消息 hello world
        String message = "HelloWorld";
        channel.basicPublish(exchange_name,routing_key , MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
        channel.close();
        connection.close();


    }
}
