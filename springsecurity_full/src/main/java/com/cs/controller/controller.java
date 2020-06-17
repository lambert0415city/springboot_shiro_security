package com.cs.controller;

import com.cs.entity.User;
import com.cs.entity.UserDto;
import com.cs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author: szh
 * @since:
 */

@RestController
public class controller {
    @Autowired
    private UserService userService;

    /**
     * 查看登录用户信息
     */
    @GetMapping("/get-auth")
    public Authentication getAuth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/getuser")
    public User getUserInfo(@RequestParam String username){
        return userService.getUserInfo(username);
    }

    @GetMapping("/get-user")
    public UserDto getUser(@RequestParam String username){
        return userService.selectUserByUsername(username);
    }

    @PostMapping("/adduser")
    public boolean addUser(@RequestBody User user){
        return userService.insertUserInfo(user);
    }

    /**
     * update user password
     * @return
     */
    @PostMapping("/updateuser")
    public boolean updateUser(@RequestBody Map<String,String> map){
        return userService.updateUserInfo(map.get("old"),map.get("new"));
    }

    @PreAuthorize("hasAnyRole('user')")
    @GetMapping("/user")
    public String user(){
        return "user rights";
    }

    @PreAuthorize("hasAnyRole('admin')")
    @GetMapping("/admin")
    public String admin(){
        return "admin rights";
    }

}
