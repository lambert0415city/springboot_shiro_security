package com.yue;

import com.yue.dao.DepartmentMapper;
import com.yue.dao.EmployeeMapper;
import com.yue.pojo.Department;
import com.yue.pojo.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootWebApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    DepartmentMapper departmentMapper;

    @Test
    void contextLoads() {
        Employee employee = new Employee(1009, "yue", "303030303@qq.com", 1, new Department(101, "教学部"));
        employeeMapper.save(employee);
    }

}
