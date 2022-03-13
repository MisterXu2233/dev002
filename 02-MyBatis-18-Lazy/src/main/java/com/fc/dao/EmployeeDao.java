package com.fc.dao;

import com.fc.entity.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeDao {
    //根据部门ID查询指定部门下的员工
    List<Employee> findById(@Param("id") Integer id);
}
