package com.kgc.hz.testcommon.service;

import com.kgc.hz.testcommon.entity.*;

import java.util.List;
import java.util.Map;

/**
 * @author: szh
 * @since:
 */
public interface GroupService {

    ResponseResult checkmessage();
    ResponseResult getAllGroups();
    /**
     * 查询自己的组
     * @param
     * @return
     */
    ResponseResult getSelfGroups(String token);

    //1 别人加入我的组 需要操作     (查看消息)
    ResponseResult getOthersApply(String token);

    //2 别人邀请我加入	需要操作   (查看消息)
    ResponseResult getOthersInvite(String token);

    //3 我想加入别人               (查看)
    ResponseResult getMyApply(String token);

    //4 我邀请别人加入             (查看)
    ResponseResult getMyInvite(String token);

    //更新
    ResponseResult updateGroup(Groupinfo groupinfo);

    //添加
    ResponseResult addGroup(Groupinfo groupinfo);

    //1用户申请加入
    ResponseResult addApply(Userapply userapply);



    //2群主同意加入
    ResponseResult updateApply(Userapply userapply);

    //3邀请用户加入
    ResponseResult addInvite(Userinvite userinvite);

    //4用户同意加入
    ResponseResult updateInvite(Userinvite userinvite);

    Groupinfo getGroupById(int id);

    //假删除
    boolean falsedelete(int id);

    //批量删除
    boolean deleteList(int[] ids);

    //删除1个
    ResponseResult deleteOne(int id);
}
