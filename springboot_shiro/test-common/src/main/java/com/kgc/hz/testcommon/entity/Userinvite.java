package com.kgc.hz.testcommon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (Userinvite)实体类
 *
 * @author makejava
 * @since 2020-03-19 16:15:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Userinvite implements Serializable {


    //用户id 与 角色id 便于添加groupmember
    private Integer userid;
    private Integer roleid;


    private Integer id;
    
    private Integer groupid;
    
    private String inviter;
    
    private String invitee;
    
    private String invitereason;
    
    private String feedbackinfo;
    
    private String feedbackstatus;
    
    private Date invitetime;
    
    private Date feedbacktime;

}