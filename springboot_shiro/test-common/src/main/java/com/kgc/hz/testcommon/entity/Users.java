package com.kgc.hz.testcommon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Users)实体类
 *
 * @author makejava
 * @since 2020-03-18 15:28:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users implements Serializable {

    private Integer id;
    
    private String username;
    
    private String password;
    
    private String realname;

    /**
     * 角色名称
     */
    private String name;
}