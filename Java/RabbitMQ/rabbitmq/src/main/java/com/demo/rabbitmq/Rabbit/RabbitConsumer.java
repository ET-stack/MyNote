package com.demo.rabbitmq.Rabbit;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * @author Yi
 * @date 2020/6/9 8:41
 */
public class RabbitConsumer {
    private static final String  queue_name = "queue_demo";
    private static final String ip_address = "localhost";
    private static final int port = 5672;

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Address [] addresses =  new Address[]{new Address(ip_address,port)};
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = factory.newConnection(addresses);
        //创建连接
        final Channel channel = connection.createChannel();
        //创建信道
        channel.basicQos(64);
        //设置客户端最多未被接收的信息个数
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope
                                        envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("recv message"+new String(body));
                try {
                    TimeUnit.SECONDS.sleep(1);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                channel.basicAck(envelope.getDeliveryTag(),false);
            }

        };
        channel.basicConsume(queue_name,consumer);
        TimeUnit.SECONDS.sleep(5);
        channel.close();
        connection.close();

    }
}
