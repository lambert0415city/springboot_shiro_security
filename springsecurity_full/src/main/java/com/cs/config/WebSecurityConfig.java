package com.cs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author: szh
 * @since:
 */

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启方法及安全验证
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 指定加密方式
     */
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        // 使用BCrypt加密密码
        return new BCryptPasswordEncoder();
    }
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 从数据库读取的用户进行身份认证
        auth.userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST,"/adduser","/updateuser").permitAll() // 允许post请求/add-user，而无需认证
                .anyRequest().authenticated()
                .and()
                .formLogin()// 使用默认的登录页面
                .and()
                // post请求要关闭csrf验证,不然访问报错；实际开发中开启，需要前端配合传递其他参数
                .csrf().disable();
    }
}
