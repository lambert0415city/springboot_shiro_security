package com.cs.dao;

import com.cs.entity.User;
import com.cs.entity.UserDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author: szh
 * @since:
 */

@Mapper
@Repository
public interface UserDao {
    @Select("select * from user where username = #{username}")
    User getUserInfo(@Param("username") String username);

    @Insert("insert into user(username,password,role) values (#{username},#{password},#{role})")
    int insertUserInfo(User user);

    @Update("update user set password = #{newpassword} where username = #{username}")
    int updateUserInfo(String username,String newpassword);

    // 查询用户
    UserDto selectUserByUsername(@Param("username") String username);
}
