package com.cs.swagger;

import com.cs.pojo.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * @author: szh
 * @since:
 */


@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    //只要这个实体在请求接口的返回值上（即使是泛型），都能映射到实体项中：
    @PostMapping("/getUser")
    public User getUser(User user){
        return user;
    }

    @ApiOperation("测试的接口")
    @PostMapping("/kuang")
    @ResponseBody
    public String kuang(@ApiParam("用户名")String username,@ApiParam("mima")String password){
        return username;
    }
}
