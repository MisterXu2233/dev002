package com.fc.dao;

import com.fc.entity.Student;

import java.util.List;

public class StudentDao {
    public List<Student> findAll() {
    }

    public interface StudentDao{
        List<Student> findAll();
    }
}
