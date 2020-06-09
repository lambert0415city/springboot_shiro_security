package com.kgc.hz.testprovider.test;

import com.kgc.hz.testcommon.entity.Users;
import com.kgc.hz.testprovider.util.ActiveUtils2;
import org.junit.Test;
import org.springframework.jms.annotation.JmsListener;

import javax.annotation.Resource;
import java.util.logging.Logger;

/**
 * @author: szh
 * @since:
 */
public class ProviderTest {
    @Resource
    private ActiveUtils2 activeUtils2;

    @Test
    public void value() {
        String queueName="QUEUE_RECEIVE_ADD_FIREND"; //自定义队列名称
        Users u = new Users(1,"2","2","2","2");
        activeUtils2.sendObjectMessage(queueName, u);   //发送到MQS
    }

    @Test
    @JmsListener(destination = "QUEUE_RECEIVE_ADD_FIREND", containerFactory = "MyjmsQueueListener") //红色为监听的队列名称
    public void receiveAddFriend(Object message) {
        Users loginuser = (Users) message;
        System.out.println("啦啦啦啦"+loginuser.toString());
    }

}
