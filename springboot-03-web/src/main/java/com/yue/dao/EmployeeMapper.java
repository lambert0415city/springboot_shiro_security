package com.yue.dao;

import com.yue.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Mapper
@Repository
public interface EmployeeMapper {
    public void save(Employee employee);
    public Collection<Employee> getAll();
    public Employee getEmployeeById(@Param("id") Integer id);
    public void delete(@Param("id") Integer id);
}
