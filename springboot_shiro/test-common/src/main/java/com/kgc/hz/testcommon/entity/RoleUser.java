package com.kgc.hz.testcommon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (RoleUser)实体类
 *
 * @author makejava
 * @since 2020-03-18 15:28:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleUser implements Serializable {

    private Integer id;
    
    private Integer roleid;
    
    private Integer userid;


}