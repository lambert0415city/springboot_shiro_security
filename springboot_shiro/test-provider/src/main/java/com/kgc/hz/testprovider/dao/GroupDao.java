package com.kgc.hz.testprovider.dao;

import com.kgc.hz.testcommon.entity.Groupinfo;
import com.kgc.hz.testcommon.entity.Groupmember;
import com.kgc.hz.testcommon.entity.Userapply;
import com.kgc.hz.testcommon.entity.Userinvite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author: szh
 * @since:
 */
@Mapper
public interface GroupDao {
    //查看全部分组
    List<Groupinfo> getAllGroups();

    //查看自己分组
    List<Groupinfo> getSelfGroups(@Param("id") int id);

    //1 别人加入我的组 需要操作     (查看消息)
    List<Userapply> getOthersApply(@Param("id") int id);

    //2 别人邀请我加入	需要操作   (查看消息)
    List<Userinvite> getOthersInvite(@Param("invitee") String invitee);

    //3 我想加入别人               (查看)
    List<Userapply> getMyApply(@Param("applyuser") String applyuser);

    //4 我邀请别人加入             (查看)
    List<Userinvite> getMyInvite(@Param("inviter") String inviter);

    //更新群组
    int updateGroup(Groupinfo groupinfo);

    //新建群组
    int addGroup(Groupinfo groupinfo);

    //添加组员
    int addMember(Groupmember groupmember);

    //1用户申请加入               (增加)
    int addApply(Userapply userapply);

    @Select("select creatorid from groupinfo where id=#{groupid}")
    int getCreatorId(@Param("groupid") int groupid);

    //2群主同意加入               (修改)
    int updateApply(Userapply userapply);

    //3邀请用户加入               (增加)
    int addInvite(Userinvite userinvite);

    //4用户同意加入               (修改)
    int updateInvite(Userinvite userinvite);

    //查找群组by id
    Groupinfo getGroupById(@Param("id") int id);

    //假删除
    int falsedelete(@Param("id") int id);

    //批量删除
    int deleteList(Map<String,Object> map);

    //删除1个
    int deleteOne(@Param("id") int id);
}
