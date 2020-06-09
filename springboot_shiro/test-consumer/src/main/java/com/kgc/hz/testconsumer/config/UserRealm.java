package com.kgc.hz.testconsumer.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.kgc.hz.testcommon.entity.Users;
import com.kgc.hz.testcommon.service.UserInfoService;
import com.kgc.hz.testconsumer.util.RedisUtil;
import com.kgc.hz.testconsumer.util.SpringBeanFactoryUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private RedisUtil redisUtil;
    /**
     * securityManager 自动调用该接口进行用户角色的授权
     * @param principalCollection
     * @return
     */
    @Override
    //授权
    protected AuthorizationInfo doGetAuthorizationInfo
                                    (PrincipalCollection principalCollection) {
        String userName = (String)principalCollection.getPrimaryPrincipal();
//        UserInfo user = userInfoService.login(userName);

        String token = redisUtil.getStr(userName);
        String user = redisUtil.getStr(token);
        Users userInfo = JSON.toJavaObject((JSON) JSONObject.parse(user), Users.class);

        SimpleAuthorizationInfo authorizationInfo =new SimpleAuthorizationInfo();
        //添加角色权限
//        authorizationInfo.addRole(user.getRoleName());
        List<String> pers = new ArrayList<>();
        if(!StringUtils.isEmpty(userInfo)){
//            pers.add("group:view");
//            StaffRole role = userInfo.getStaffRole();
//            if(EmptyUtils.isNotEmpty(role)){
//                List<StaffPermission> pList = role.getPermissionList();
//                if(EmptyUtils.isNotEmpty(pList)){
//                    for(StaffPermission permission : pList){
//                        pers.add(permission.getName());
//                    }
//                }
//            }
        }

        if(userInfo.getName().equals("超级管理员")){
            pers.add("user:add");
            pers.add("user:update");
            pers.add("user:delete");
            pers.add("group:view");
            pers.add("self:view");
        }

        if(userInfo.getName().equals("普通管理员")){
            pers.add("user:add");
            pers.add("user:update");
            pers.add("group:view");
            pers.add("self:view");
        }

        if(userInfo.getName().equals("普通用户")){
            pers.add("self:view");
        }

        if(userInfo.getName().equals("群组创建者")){
            pers.add("self:view");
        }

        if(userInfo.getName().equals("群组管理员")){
            pers.add("self:view");
        }

        if(userInfo.getName().equals("群组用户")){
            pers.add("self:view");
        }
        authorizationInfo.addStringPermissions(pers);
//        authorizationInfo.addStringPermission("user:add");


        return authorizationInfo;
    }

    /**
     * securityManager 自动调用该接口进行用户的权限验证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo
    (AuthenticationToken authenticationToken) throws AuthenticationException {
        String user = (String) authenticationToken.getPrincipal();

        if(user==null){
            return null;
        }

        UserInfoService userInfoService = SpringBeanFactoryUtils.getBean("userInfoService", UserInfoService.class);

        Users userInfo = new Users();
        userInfo.setUsername(user);

        //登录权限验证
        Users u = userInfoService.loginUser(userInfo);
        if(u==null){
            return null;
        }else{
            SimpleAuthenticationInfo info =
                    new SimpleAuthenticationInfo
                            (u.getUsername().toString(),u.getPassword().toString(),getName());
            return info;
        }
    }
}
