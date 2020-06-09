package com.kgc.hz.testprovider.rabbitmq;

import com.kgc.hz.testcommon.entity.Users;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: szh
 * @since:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitTest {
    @Autowired
    private Provider sender;

    // 发送单条消息
    @org.junit.Test
    public void contextLoads() {
        sender.send();
    }


    // 发送对象
    @org.junit.Test
    public void sendObj() {
        Users user = new Users();
        user.setId(1);
        user.setUsername("admin");
        user.setPassword("1234");
        sender.send(user);
    }

    //Header
    // 发送者日志
//    credit.bank send message: 银行授信(部分匹配)
//    credit.finance send message: 金融公司授信(全部匹配)
//    credit.bank send message: 银行授信(全部匹配)
//    credit.finance send message: 金融公司授信(部分匹配)
    // 接收者日志
//    credit.bank receive message: 银行授信(全部匹配)
//    credit.bank receive message: 金融公司授信(全部匹配)
//    credit.bank receive message: 金融公司授信(部分匹配)
    @org.junit.Test
    public void test_creditBank_type() {
        Map<String,Object> head = new HashMap<>();
        head.put("type", "cash");
        sender.creditBank(head, "银行授信(部分匹配)");
    }

    @org.junit.Test
    public void test_creditBank_all() {
        Map<String,Object> head = new HashMap<>();
        head.put("type", "cash");
        head.put("aging", "fast");
        sender.creditBank(head, "银行授信(全部匹配)");
    }

    @org.junit.Test
    public void test_creditFinance_type() {
        Map<String,Object> head = new HashMap<>();
        head.put("type", "cash");
        sender.creditFinance(head, "金融公司授信(部分匹配)");
    }

    @org.junit.Test
    public void test_creditFinance_all() {
        Map<String,Object> head = new HashMap<>();
        head.put("type", "cash");
        head.put("aging", "fast");
        sender.creditFinance(head, "金融公司授信(全部匹配)");
    }

}
