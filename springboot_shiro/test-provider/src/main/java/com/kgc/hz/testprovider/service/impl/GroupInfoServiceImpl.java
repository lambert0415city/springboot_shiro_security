package com.kgc.hz.testprovider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kgc.hz.testcommon.entity.*;
import com.kgc.hz.testcommon.service.GroupService;
import com.kgc.hz.testcommon.service.UserInfoService;
import com.kgc.hz.testcommon.utils.EmptyUtils;
import com.kgc.hz.testprovider.dao.GroupDao;
import com.kgc.hz.testprovider.util.ActiveMQUtils;
import com.kgc.hz.testprovider.util.RedisUtil;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.jms.JMSException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: szh
 * @since:
 */
@Component
@Service(interfaceClass = GroupService.class)
public class GroupInfoServiceImpl implements GroupService {

    @Resource
    private ActiveMQUtils activeMQUtils;

    @Resource
    private GroupDao groupDao;

    @Resource
    private RedisUtil redisUtil;

    //测试消息队列方法-
    @Override
    public ResponseResult checkmessage() {
        //获取消息队列中的applyuesr对象
        Userapply userapply = null;
        try {
            userapply = (Userapply) activeMQUtils.receive("addapply");
        } catch (JMSException e) {
            e.printStackTrace();
        }

        //看消息是发到那个群主
        int creatorId = groupDao.getCreatorId(userapply.getGroupid());

        //当前用户登录id
        int loginid = (int) redisUtil.getObj("loginid");
        if(loginid == creatorId){
            return new ResponseResult(true,4,"您有新的消息");
        }
        return new ResponseResult(false,5,"没有消息");

    }

    //传入当前用户，查看是否有通知消息提醒
//    @Transactional
//    @JmsListener(destination = "addapply")
//    public boolean quickgetOthersApply(Object message){
//        Users loginuser = (Users) message;
//
//        //获取消息队列中的applyuesr对象
//        Userapply userapply = null;
//        try {
//            userapply = (Userapply) activeMQUtils.receive("addapply");
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//
//        int creatorId = groupDao.getCreatorId(userapply.getGroupid());
//
//        if(loginuser.getId() == creatorId){
//            return true;
//        }
//        return false;
//    }

    /**
     * 所有组
     * @return
     */
    @Override
    public ResponseResult getAllGroups() {
        List<Groupinfo> allGroups = groupDao.getAllGroups();
        if(EmptyUtils.isNotEmpty(allGroups)){
            return new ResponseResult(true,4,allGroups);
        }

        return new ResponseResult(false,0,"查询失败");
    }

    /**
     * 查看自己的组
     * @param
     * @return
     */
    @Override
    public ResponseResult getSelfGroups(String token) {
        String user = redisUtil.getStr(token);

        if(EmptyUtils.isNotEmpty(user)){
            Users loginuser = JSON.toJavaObject((JSON) JSONObject.parse(user),
                    Users.class);

            redisUtil.setObj("loginid",loginuser.getId());

            //异步查看是否有新消息
//            activeMQUtils.sendQueueMesage("checkapply",loginuser);

            if(EmptyUtils.isNotEmpty(loginuser)){
                List<Groupinfo> selfGroups = groupDao.getSelfGroups(loginuser.getId());
                return selfGroups != null ?
                        new ResponseResult(true,4,selfGroups)
                        :new ResponseResult(false,0,"查询失败或者该用户没有任何组");
            }
        }
        return new ResponseResult(false,5,"获取用户token失败");
    }

    @Override
    //1别人加入我的组，需要操作
    public ResponseResult getOthersApply(String token) {
        String user = redisUtil.getStr(token);

        if(EmptyUtils.isNotEmpty(user)){
            Users loginuser = JSON.toJavaObject((JSON) JSONObject.parse(user),
                    Users.class);

            if(EmptyUtils.isNotEmpty(loginuser)){
                List<Userapply> userapplies = groupDao.getOthersApply(loginuser.getId());
                return userapplies != null ?
                        new ResponseResult(true,4,userapplies)
                        :new ResponseResult(false,0,"用户没有任何别人加我的申请");
            }
        }
        return new ResponseResult(false,5,"获取用户token失败");
    }

    @Override
    //2 别人邀请我的信息，需要操作
    public ResponseResult getOthersInvite(String token) {
        String user = redisUtil.getStr(token);

        if(EmptyUtils.isNotEmpty(user)){
            Users loginuser = JSON.toJavaObject((JSON) JSONObject.parse(user),
                    Users.class);

            if(EmptyUtils.isNotEmpty(loginuser)){
                List<Userinvite> userinvites = groupDao.getOthersInvite(loginuser.getUsername());
                return userinvites != null ?
                        new ResponseResult(true,4,userinvites)
                        :new ResponseResult(false,0,"用户没有任何别人的邀请");
            }
        }
        return new ResponseResult(false,5,"获取用户token失败");
    }

    @Override
    public ResponseResult getMyApply(String token) {
        String user = redisUtil.getStr(token);

        if(EmptyUtils.isNotEmpty(user)){
            Users loginuser = JSON.toJavaObject((JSON) JSONObject.parse(user),
                    Users.class);

            if(EmptyUtils.isNotEmpty(loginuser)){
                List<Userapply> userapplies = groupDao.getMyApply(loginuser.getUsername());
                return userapplies != null ?
                        new ResponseResult(true,4,userapplies)
                        :new ResponseResult(false,0,"还没有我想加入别人的信息");
            }
        }
        return new ResponseResult(false,5,"获取用户token失败");
    }

    @Override
    public ResponseResult getMyInvite(String token) {
        String user = redisUtil.getStr(token);

        if(EmptyUtils.isNotEmpty(user)){
            Users loginuser = JSON.toJavaObject((JSON) JSONObject.parse(user),
                    Users.class);

            if(EmptyUtils.isNotEmpty(loginuser)){
                List<Userinvite> userinvites = groupDao.getMyInvite(loginuser.getUsername());
                return userinvites != null ?
                        new ResponseResult(true,4,userinvites)
                        :new ResponseResult(false,0,"你还没有邀请任何人");
            }
        }
        return new ResponseResult(false,5,"获取用户token失败");
    }


    @Override
    public ResponseResult updateGroup(Groupinfo groupinfo) {
        if(StringUtils.isEmpty(groupinfo.getId())){
            return new ResponseResult(false,0,"空id参数");
        }
        return  groupDao.updateGroup(groupinfo)  > 0 ?
                new ResponseResult(true,4,"修改成功"):
                new ResponseResult(false,5,"修改失败");

    }

    @Override
    public ResponseResult addGroup(Groupinfo groupinfo) {
        if(StringUtils.isEmpty(groupinfo)){
            return new ResponseResult(false,0,"t添加空参数");
        }

        return  groupDao.addGroup(groupinfo)  > 0 ?
                new ResponseResult(true,4,"添加成功"):
                new ResponseResult(false,5,"添加失败");

    }

    @Override
    //1用户申请加入
    public ResponseResult addApply(Userapply userapply) {
        if(StringUtils.isEmpty(userapply)){
            return new ResponseResult(false,0,"用户申请空参数");
        }

        //通过消息队列异步订阅消息 操作数据库
        activeMQUtils.sendQueueMesage("addapply",userapply.toString());
        activeMQUtils.sendQueueMesage("pairtest","pairtest");

        return  groupDao.addApply(userapply)  > 0 ?
                new ResponseResult(true,4,"申请成功"):
                new ResponseResult(false,5,"申请失败");
    }


    //2群主同意加入
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseResult updateApply(Userapply userapply) {
        if(StringUtils.isEmpty(userapply)){
            return new ResponseResult(false,0,"userapply空参数");
        }

        //群主拒绝
        if(userapply.getCheckstatus().equals("false")){
            return  groupDao.updateApply(userapply)  > 0 ?
                    new ResponseResult(true,5,"群主拒绝加入"):
                    new ResponseResult(false,3,"更新userapply失败");
        }

        Groupmember groupmember = new Groupmember();
        groupmember.setGroupid(userapply.getGroupid());
        groupmember.setMemberid(userapply.getUserid());
        groupmember.setRoleid(userapply.getRoleid());

        if( groupDao.updateApply(userapply) > 0 && groupDao.addMember(groupmember) >0){

            return new ResponseResult(true,4,"群主同意，申请加入成功");
        }else if( groupDao.updateApply(userapply) > 0){
            return new ResponseResult(false,0,"userapply更新，groupmember添加出错");
        }else if(groupDao.addMember(groupmember) >0){
            return new ResponseResult(false,1,"userapply更新出错，groupmember添加成功");
        }else {
            return new ResponseResult(false,2,"添加组员全部失败");
        }
    }

    //3群主邀请加入
    @Override
    public ResponseResult addInvite(Userinvite userinvite) {
        if(StringUtils.isEmpty(userinvite)){
            return new ResponseResult(false,0,"群主邀请空参数");
        }

        return  groupDao.addInvite(userinvite)  > 0 ?
                new ResponseResult(true,4,"邀请成功"):
                new ResponseResult(false,5,"邀请失败");
    }

    //4用户同意邀请
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseResult updateInvite(Userinvite userinvite) {
        if(StringUtils.isEmpty(userinvite)){
            return new ResponseResult(false,0,"userinvite空参数");
        }

        Groupmember groupmember = new Groupmember();
        groupmember.setGroupid(userinvite.getGroupid());
        groupmember.setMemberid(userinvite.getUserid());
        groupmember.setRoleid(userinvite.getRoleid());

        //群主拒绝
        if(userinvite.getFeedbackstatus().equals("false")){
            return  groupDao.updateInvite(userinvite)  > 0 ?
                    new ResponseResult(true,5,"用户拒绝加入"):
                    new ResponseResult(false,3,"更新userinvite失败");
        }

        if(groupDao.updateInvite(userinvite) > 0 && groupDao.addMember(groupmember) >0){
            return new ResponseResult(true,4,"用户同意，邀请组员成功");
        }else if( groupDao.updateInvite(userinvite) > 0){
            return new ResponseResult(false,0,"userinvite，groupmember添加出错");
        }else if(groupDao.addMember(groupmember) >0){
            return new ResponseResult(false,1,"userinvite，groupmember添加成功");
        }else {
            return new ResponseResult(false,2,"添加组员全部失败");
        }
    }

    @Override
    public Groupinfo getGroupById(int id) {
        if(StringUtils.isEmpty(id)){
            return null;
        }
        return groupDao.getGroupById(id);
    }

    @Override
    public boolean falsedelete(int id) {
        return groupDao.falsedelete(id)>0;
    }

    @Override
    public boolean deleteList(int[] ids) {
        Map<String,Object> map = new HashMap<>();
        map.put("ids",ids);
        return groupDao.deleteList(map) == ids.length;
    }

    @Override
    public ResponseResult deleteOne(int id) {
        if(StringUtils.isEmpty(id)){
            return new ResponseResult(false,0,"空id参数");
        }
        return  groupDao.deleteOne(id)  > 0 ?
                new ResponseResult(true,4,"删除成功"):
                new ResponseResult(false,5,"删除失败");
    }
}
