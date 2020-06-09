package com.kgc.hz.testprovider.test;

import com.kgc.hz.testcommon.entity.Users;
import com.kgc.hz.testprovider.util.ActiveUtils2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: szh
 * @since:
 */
@RestController
public class ProviderControllerTest {

    @Resource
    private ActiveUtils2 activeUtils2;

    @GetMapping("/value")
    public String value() {
        String queueName="QUEUE_RECEIVE_ADD_FIREND"; //自定义队列名称
        Users u = new Users();
        activeUtils2.sendObjectMessage(queueName, u);   //发送到MQS
        return "消息已经发送";
    }

}
