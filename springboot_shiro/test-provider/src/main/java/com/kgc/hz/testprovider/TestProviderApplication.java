package com.kgc.hz.testprovider;

import com.alibaba.boot.dubbo.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDubboConfiguration
@EnableTransactionManagement
//启动消息队列
@EnableJms
public class TestProviderApplication{
    public static void main(String[] args) {
        SpringApplication.run(TestProviderApplication.class, args);
    }
}
