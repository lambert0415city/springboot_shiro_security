package com.kgc.hz.testprovider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kgc.hz.testcommon.entity.Users;
import com.kgc.hz.testcommon.service.UserInfoService;
import com.kgc.hz.testcommon.utils.EmptyUtils;
import com.kgc.hz.testcommon.utils.TokenUtils;
import com.kgc.hz.testprovider.dao.UserInfoDao;
import com.kgc.hz.testprovider.util.RedisUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author: szh
 * @since:
 */
@Component
@Service(interfaceClass = UserInfoService.class)
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private RedisUtil redisUtil;


    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public Users loginUser(Users user) {
        if(EmptyUtils.isEmpty(user)){
            return null;
        }

        Users info = userInfoDao.getUser(user);
        if(EmptyUtils.isNotEmpty(info)){
            String token = redisUtil.getStr(info.getUsername());
            //如果用户在redis中已经存在，证明该用户已经在别处登录，
            //删除原来的redis缓存，当前用户挤掉之前的用户登录进去
            if(!StringUtils.isEmpty(token)){
                redisUtil.del(info.getUsername());
                redisUtil.del(token);
            }
            //为当前用户生成新的token值
            String newsToken = "userToken:"+ TokenUtils
                    .createToken(String.valueOf(info.getId()),info.getUsername());
            //ba新建的当前用户的token存至redis中,token的有效时间为30分钟
            redisUtil.setStr(newsToken, JSON.toJSONString(info));
            redisUtil.setStr(String.valueOf(info.getUsername()),newsToken,30L);
            return info;
        }
        return null;

    }

    @Override
    public boolean logout(String token) {
        if(EmptyUtils.isNotEmpty(token)){
            String user = redisUtil.getStr(token);
            if(EmptyUtils.isNotEmpty(user)){
                Users userinfo = JSON.toJavaObject(
                        (JSON) JSONObject.parse(user),Users.class);
                if (EmptyUtils.isNotEmpty(userinfo)) {
//                    String id = String.valueOf(userInfo.getId());
                    redisUtil.del(userinfo.getUsername());
                    redisUtil.del(token);
                    return true;
                }
            }
        }
        return true;
    }
}
