package com.kgc.hz.testprovider.dao;

import com.kgc.hz.testcommon.entity.Users;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: szh
 * @since:
 */
@Mapper
public interface UserInfoDao {
    Users getUser(Users user);
}
