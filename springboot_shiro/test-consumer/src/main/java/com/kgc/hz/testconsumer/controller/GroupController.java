package com.kgc.hz.testconsumer.controller;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kgc.hz.testcommon.entity.*;
import com.kgc.hz.testcommon.service.GroupService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author: szh
 * @since:
 */
@Controller
public class GroupController {
    @DubboConsumer
    private GroupService groupService;

    @Bean(name = "groupService")
    public GroupService getUserInfoService() {
        return groupService;
    }

    public void setUserInfoService(GroupService userInfoService) {
        this.groupService = groupService;
    }

    /**
     * 查看所有组
     * @return
     */
    @RequiresPermissions("group:view")
    @ResponseBody
    @GetMapping(value = "/allgroups")
    public Object getAllGroups(){
        return JSON.toJSONStringWithDateFormat(groupService.getAllGroups(),JSON.DEFFAULT_DATE_FORMAT);
    }

    /**
     * 查看当前组
     * @return
     */
    @ResponseBody
    @RequestMapping("/selfgroups")
    public Object getSelfGroups(String token){
        return JSON.toJSONStringWithDateFormat(groupService.getSelfGroups(token),JSON.DEFFAULT_DATE_FORMAT);
    }

    //实时查看消息的方法
    @ResponseBody
    @RequestMapping("/checkmessages")
    public Object checkmessages(){
        return JSON.toJSONString(groupService.checkmessage());
    }

    //1查看别人加入我的组 需要操作
    @ResponseBody
    @RequestMapping("/getothersapply")
    public Object getOthersApply(String token){
        return JSON.toJSONStringWithDateFormat(groupService.getOthersApply(token),JSON.DEFFAULT_DATE_FORMAT);
    }

    //2别人邀请我加入	需要操作
    @ResponseBody
    @RequestMapping("/getothersinvite")
    public Object getOthersInvite(String token){
        return JSON.toJSONStringWithDateFormat(groupService.getOthersInvite(token),JSON.DEFFAULT_DATE_FORMAT);
    }

    //3查看别我想加入别人
    @ResponseBody
    @RequestMapping("/getmyapply")
    public Object getMyApply(String token){
        return JSON.toJSONStringWithDateFormat(groupService.getMyApply(token),JSON.DEFFAULT_DATE_FORMAT);
    }

    //4 查看我邀请别人加入
    @ResponseBody
    @RequestMapping("/getmyinvite")
    public Object getMyInvite(String token){
        return JSON.toJSONStringWithDateFormat(groupService.getMyInvite(token),JSON.DEFFAULT_DATE_FORMAT);
    }

    /**
     * 添加接口
     * @param
     * @return
     */
    @ResponseBody
//    @RequiresPermissions("user:add")
    @RequestMapping(value = "/addgroup",method = RequestMethod.POST)
    public Object addGroup(Groupinfo groupinfo){
        return  JSON.toJSONString(groupService.addGroup(groupinfo));
    }

    //1用户申请加入
    @ResponseBody
    @RequestMapping(value = "/userapply",method = RequestMethod.POST)
    public Object addApply(Userapply userapply){
        return  JSON.toJSONString(groupService.addApply(userapply));
    }

    //2群主同意
    @ResponseBody
    @RequestMapping(value = "/leaderagree",method = RequestMethod.POST)
    public Object updateApply(Userapply userapply){
        return  JSON.toJSONString(groupService.updateApply(userapply));
    }

    //3群主发起邀请
    @ResponseBody
    @RequestMapping(value = "/leaderinvite",method = RequestMethod.POST)
    public Object addInvite(Userinvite userinvite){
        return  JSON.toJSONString(groupService.addInvite(userinvite));
    }

    //4用户同意
    @ResponseBody
    @RequestMapping(value = "/useragree",method = RequestMethod.POST)
    public Object updateInvite(Userinvite userinvite){
        return  JSON.toJSONString(groupService.updateInvite(userinvite));
    }


    /**
     * 修改接口
     * @param
     * @return
     */
    @ResponseBody
//    @RequiresPermissions("user:update")
    @RequestMapping(value = "/updategroup",method = RequestMethod.POST)
    public Object updateGroup(Groupinfo groupinfo){
        return  JSON.toJSONString(groupService.updateGroup(groupinfo));
    }

    //删除接口
    @ResponseBody
    @RequiresPermissions("user:delete")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public Object delete(@PathVariable int id){
        return  JSON.toJSONString(groupService.deleteOne(id));
    }

    //批量删除
    @ResponseBody
    @RequiresPermissions("user:delete")
    @RequestMapping(value = "/deletes",method = RequestMethod.GET)
    public Object delete(int[] ids){
        return  JSON.toJSONString(groupService.deleteList(ids));
    }

    //假的删除接口
    @ResponseBody
    @RequiresPermissions("user:delete")
    @RequestMapping(value = "/falsedelete",method = RequestMethod.GET)
    public Object falsedelete(int id){
        return groupService.falsedelete(id);
    }

    //进入修改页面
//    @RequiresPermissions("user:update")
//    @RequestMapping(value = "/updatetalent/{id}",method = RequestMethod.GET)
//    public String showUpdateTalent(@PathVariable int id, Model model){
//        EnterpriseTalent talent = enterpriseService.getEnterpriseById(id);
//        model.addAttribute("talent",talent);
//        return "updatetalent";
//    }
}
