package com.yue.controller;

import com.yue.dao.DepartmentDao;
import com.yue.dao.DepartmentMapper;
import com.yue.dao.EmployeeDao;
import com.yue.dao.EmployeeMapper;
import com.yue.pojo.Department;
import com.yue.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebParam;
import java.util.Collection;

@Controller
public class EmployeeController {
    //不整合mybatis将下面的改为注入dao即可
    @Autowired
    EmployeeMapper employeeDao;
    @Autowired
    DepartmentMapper departmentDao;

    @RequestMapping("/getall")
    String getAll(Model model){
        Collection<Employee> all = employeeDao.getAll();
        model.addAttribute("list",all);
        return "emp/list";
    }

    @GetMapping("/emp")
    public String toAdd(Model model){
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departments",departments);
        return "emp/add";
    }
    @PostMapping("/emp")
    public String add(Employee employee){
        employeeDao.save(employee);
        return "redirect:/getall";
    }

    @GetMapping("/emp/{id}")
    public String toUpateEmp(@PathVariable("id") Integer id,Model model){
        Employee employee = employeeDao.getEmployeeById(id);
        model.addAttribute("emp",employee);
        model.addAttribute("departments",departmentDao.getDepartments());
        return "emp/update";
    }
    @PostMapping("/updateEmp")
    public String updateEmp(Employee employee){
        employeeDao.save(employee);
        return "redirect:/getall";
    }

    @GetMapping("/delemp/{id}")
    public String deleteEmp(@PathVariable("id") Integer id){
        employeeDao.delete(id);
        return "redirect:/getall";
    }
}
