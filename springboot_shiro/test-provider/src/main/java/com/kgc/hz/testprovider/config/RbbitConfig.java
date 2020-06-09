package com.kgc.hz.testprovider.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * @author: szh
 * @since:
 */
@Configuration
public class RbbitConfig {

    @Bean
    public Queue string() {
        return new Queue("hello");
    }
}
