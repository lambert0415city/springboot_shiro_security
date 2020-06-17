package com.cs.config;

import com.cs.entity.User;
import com.cs.service.UserService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 要从数据库读取用户信息进行身份认证，需要新建类实现UserDetailService接口重写
 * @author: szh
 * @since:
 */

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserInfo(username);
        if(null == user){
            throw new UsernameNotFoundException("User doesn't exist");
        }

        // 得到用户角色
        String role = user.getRole();

        // 角色集合
        List<GrantedAuthority> authorityList = new ArrayList<>();

        // 角色必须以`ROLE_`开头，数据库中没有，则在这里加
        authorityList.add(new SimpleGrantedAuthority("ROLE_"+role));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
//                  // 数据库密码已加密，不用再加密
//                passwordEncoder.encode(user.getPassword()),
                authorityList);
    }
}
