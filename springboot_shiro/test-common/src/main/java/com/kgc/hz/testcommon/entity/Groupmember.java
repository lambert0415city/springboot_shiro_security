package com.kgc.hz.testcommon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (Groupmember)实体类
 *
 * @author makejava
 * @since 2020-03-18 15:28:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Groupmember implements Serializable {
    
    private Integer id;
    
    private Integer groupid;
    
    private Integer memberid;
    
    private Integer roleid;
    
    private Date createtime;

}