package com.yue.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @RequestMapping("/login")
    public String login(Model model, @RequestParam("username") String username, @RequestParam("password") String password, HttpSession session){
        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
            model.addAttribute("msg","用户名或密码不可为空");
            return "index";
        }

        if(!username.equals("admin")||!password.equals("123")) {
            model.addAttribute("msg","用户名或密码错误");
                    //这里请求的是index.html还是根据mvcconfig来看?
                    return "index";
        }
        session.setAttribute("user",username);
        return "redirect:main.html";
    }

    // 注销
    @RequestMapping("/uesr/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/index.html";
    }
}
