package com.kgc.hz.testprovider.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: szh
 * @since:
 */
@Component
public class Consumer {

    //Direct模式相当于一对一模式
    @RabbitHandler
    @RabbitListener(queues = "hello")
    public void process(String hello) {
        System.out.println("Receiver  : " + hello);
    }

    //RabbitMQ接收
    @RabbitHandler
    @RabbitListener(queues = "object")
    public void process(Object user) {
        System.out.println("Receiver object : " + user);
//        System.out.println("username:"+user.getUsername());
//        System.out.println("password:"+user.getPassword());
    }

    //Topic
//    @Component
//    @RabbitListener(queues = "topic.message")
//    public class TopicReceiver {
//        @RabbitHandler
//        @RabbitListener(queues = "topic.message")
//        public void process(String message) {
//            System.out.println("Topic Receiver1  : " + message);
//        }
//    }


    //Header
    @RabbitHandler
    @RabbitListener(queues = "credit.bank")
    public void creditBank(String msg) {
        System.out.println("credit.bank receive message: "+msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "credit.finance")
    public void creditFinance(String msg) {
        System.out.println("credit.bank receive message: "+msg);
    }

    //fanout
    @RabbitHandler
    public void fanoutprocess(String message) {
        System.out.println("fanout Receiver A  : " + message);
    }

}
