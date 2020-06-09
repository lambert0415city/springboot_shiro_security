package com.kgc.hz.testprovider.config;
import javax.jms.Queue;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author: szh
 * @since:
 */
@Configuration
public class BeanConfig {
    //定义存放消息
    @Bean
    public Queue queue(){
        return new ActiveMQQueue("ActiveMQQueue");
    }
}
