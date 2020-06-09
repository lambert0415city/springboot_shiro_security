package com.kgc.hz.testconsumer.controller;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.kgc.hz.testcommon.entity.ResponseResult;
import com.kgc.hz.testcommon.entity.Users;
import com.kgc.hz.testcommon.service.UserInfoService;
import com.kgc.hz.testcommon.utils.EmptyUtils;
import com.kgc.hz.testconsumer.util.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author: szh
 * @since:
 */
@Controller
public class UserController {

    @Resource
    private RedisUtil redisUtil;

    @DubboConsumer
    private UserInfoService userInfoService;

    @Bean(name = "userInfoService")
    public UserInfoService getUserInfoService() {
        return userInfoService;
    }

    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @ResponseBody
    @PostMapping(value = "/loginuser")
    public ResponseResult loginUser(Users user){
        if (EmptyUtils.isNotEmpty(user)) {
            //进行加密
            user.setPassword(com.kgc.hz.testcommon.utils.SecurityUtils.md5Hex(user.getPassword()) );
        }

        //  获取当前用户信息
        Subject subject = SecurityUtils.getSubject();

        //封装用户数据
        UsernamePasswordToken usernamePasswordToken =
                new UsernamePasswordToken(user.getUsername(),user.getPassword());

        ResponseResult responseResult = null;
        try{
            subject.login(usernamePasswordToken);
            String token = redisUtil.getStr(user.getUsername());
            responseResult = new ResponseResult(true,3,token);

        }catch (AuthenticationException e){
            e.printStackTrace();
            responseResult = new ResponseResult(false,0,"用户名密码错误");
        }catch (AuthorizationException e){
            e.printStackTrace();
            responseResult = new ResponseResult(false,1,"没权限");
        }

        return responseResult;
    }

    //    @RequiresPermissions("user:view")
    @ResponseBody
    @GetMapping(value = "/logOut")
    public Object loginout(@RequestParam String token){
        return userInfoService.logout(token);
    }

}
