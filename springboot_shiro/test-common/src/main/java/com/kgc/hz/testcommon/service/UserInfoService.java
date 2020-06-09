package com.kgc.hz.testcommon.service;

import com.kgc.hz.testcommon.entity.ResponseResult;
import com.kgc.hz.testcommon.entity.Users;

/**
 * @author: szh
 * @since:
 */
public interface UserInfoService
{
    /**
     * 登录
     * @param
     * @return
     */
    Users loginUser(Users user);

    boolean logout(String token);
}
