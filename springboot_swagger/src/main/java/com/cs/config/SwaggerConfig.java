package com.cs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @author: szh
 * @since:
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    //如何配置多个分组？配置多个分组只需要配置多个docket即可：
    @Bean
    public Docket docket1(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("group1");
    }
    @Bean
    public Docket docket2(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("group2");
    }
    @Bean
    public Docket docket3(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("group3");
    }

    //1、Swagger实例Bean是Docket，所以通过配置Docket实例来配置Swaggger。
    @Bean
    public Docket docket(Environment environment){
        //如何动态配置当项目处于test、dev环境时显示swagger，处于prod时不显示？

        // 设置要显示swagger的环境
        Profiles of = Profiles.of("dev", "test");
        // 判断当前是否处于该环境

        // 通过 enable() 接收此参数判断是否要显示
        boolean b = environment.acceptsProfiles(of);
        System.out.println(b);


        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("配置分组") // 配置分组
                .enable(true) //配置是否启用Swagger，如果是false，在浏览器将无法访问
                .select()// 通过.select()方法，去配置扫描接口,RequestHandlerSelectors配置如何扫描接口
                .apis(RequestHandlerSelectors.basePackage("com.cs.swagger.HelloController"))
                // 配置如何通过path过滤,即这里只扫描请求以/kuang开头的接口
                .build();

        //                .paths(PathSelectors.ant("/some/**"))
//        .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
//        .apis(RequestHandlerSelectors.basePackage("com.cs.swagger.HelloController"))
//        any() // 任何请求都扫描
//        none() // 任何请求都不扫描
//        regex(final String pathRegex) // 通过正则表达式控制
//        ant(final String antPattern) // 通过ant()控制
    }

    //配置文档信息
    private ApiInfo apiInfo() {
        Contact contact = new Contact("联系人名字", "http://xxx.xxx.com/联系人访问链接", "联系人邮箱");
        return new ApiInfo(
                "Swagger学习", // 标题
                "学习演示如何配置Swagger", // 描述
                "v1.0", // 版本
                "http://terms.service.url/组织链接", // 组织链接
                contact, // 联系人信息
                "Apach 2.0 许可", // 许可
                "许可链接", // 许可连接
                new ArrayList<>()// 扩展
        );
    }
}
