package com.fc.dao;

import com.fc.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentDao {



    //增
    int insert(Student student);

    //删
    int delete(@Param("id")Integer id);

    //改
    int update(Student student);

    //查询全部
    List<Student> findAll();

    //查询全部
    Student findById(@Param("id")Integer id);

    //模糊查询
    List<Student> findByword(@Param("keyword") String keyword);

    //主键回值
    int getIncrement(Student student);

    List<Student> findByKeyword(String 士);
}