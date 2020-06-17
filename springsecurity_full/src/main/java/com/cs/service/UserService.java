package com.cs.service;

import com.cs.entity.User;
import com.cs.entity.UserDto;
import org.apache.ibatis.annotations.Param;

/**
 * @author: szh
 * @since:
 */
public interface UserService {
    User getUserInfo(String username);

    boolean insertUserInfo(User user);

    boolean updateUserInfo(String o ,String n);

    UserDto selectUserByUsername(String username);
}
