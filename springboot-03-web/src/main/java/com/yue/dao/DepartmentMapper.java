package com.yue.dao;

import com.yue.pojo.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Mapper
@Repository
public interface DepartmentMapper {
    public Collection<Department> getDepartments();
    public Department getDepartmentById(@Param("id") Integer id);
}
