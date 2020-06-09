package com.kgc.hz.testprovider.test;
import com.kgc.hz.testcommon.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
/**
 * @author: szh
 * @since:
 */

@Component
@Slf4j
public class ConsumerControllerTest {

    @JmsListener(destination = "QUEUE_RECEIVE_ADD_FIREND", containerFactory = "MyjmsQueueListener") //红色为监听的队列名称
    public void receiveAddFriend(Users student) {
        System.out.println("啦啦啦啦"+student.toString());
        log.error("receiveAddFriend Exception:{}");
    }
}
