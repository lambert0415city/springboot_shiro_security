package com.cs.service.Impl;

import com.cs.dao.UserDao;
import com.cs.entity.User;
import com.cs.entity.UserDto;
import com.cs.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * @author: szh
 * @since:
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserInfo(String username) {
        return userDao.getUserInfo(username);
    }

    @Override
    public UserDto selectUserByUsername(String username) {
        return userDao.selectUserByUsername(username);
    }

    @Override
    public boolean insertUserInfo(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.insertUserInfo(user)>0;
    }

    @Override
    public boolean updateUserInfo(String o ,String n) {
        // 获取当前登录用户信息(注意：没有密码的)
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = principal.getUsername();
        // 通过用户名获取到用户信息（获取密码）
        User user = userDao.getUserInfo(username);

        if(passwordEncoder.matches(o,user.getPassword())){
            //新密码
            return userDao.updateUserInfo(username,passwordEncoder.encode(n))>0;
        }

        return false;

    }
}
