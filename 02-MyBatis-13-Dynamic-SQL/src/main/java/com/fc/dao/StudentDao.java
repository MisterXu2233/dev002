package com.fc.dao;

import com.fc.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public abstract class StudentDao {
    public abstract List<Student> findAll();

    //模糊查询
    public abstract List<Student> findByKeyword(@Param("name") String name, @Param("age") Integer age);
    public abstract List<Student> findByStudent(Student student);
    public abstract int update(Student student);
}
