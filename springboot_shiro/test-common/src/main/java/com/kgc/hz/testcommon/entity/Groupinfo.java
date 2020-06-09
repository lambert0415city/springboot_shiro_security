package com.kgc.hz.testcommon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * (Groupinfo)实体类
 *
 * @author makejava
 * @since 2020-03-18 15:28:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Groupinfo implements Serializable {

    private Users user;

    private Groupmember groupmember;

    private Role role;

    private String requirecheck;

    private Integer id;
    
    private String name;
    
    private Integer typeid;
    
    private String picimg;
    
    private String description;
    
    private Integer creatorid;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createtime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifytime;
    /**
    * 0 siyou 1 gongyou
    */
    private Integer status;


}