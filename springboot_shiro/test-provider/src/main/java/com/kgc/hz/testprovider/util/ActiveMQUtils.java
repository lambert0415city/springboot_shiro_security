package com.kgc.hz.testprovider.util;

/*import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;*/
//import org.springframework.stereotype.Component;

//import javax.jms.Destination;



import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.*;

/***
 * ActiveMQ 工具类
 */
@Component
public class ActiveMQUtils {

    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;

//    @Resource
//    private Queue queue;
//
//    @Resource
//    private Topic topic;

    public void sendQueueMesage(String name, Object message){
        Destination destination= new ActiveMQQueue(name);
        jmsMessagingTemplate.convertAndSend(destination,message);
    }

    public Object receive(String destination) throws JMSException,NullPointerException {
        ObjectMessage objectMessage = (ObjectMessage) jmsMessagingTemplate.receive(destination);
        System.out.println("从队列" + destination.toString() + "收到了消息：\t" + objectMessage);
        return objectMessage;
    }



}
