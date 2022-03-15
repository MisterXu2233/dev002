package com.fc.test;

import com.fc.entity.Student;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CreateTest {
    @Test
    public void testConstructor() {
        //获取容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        //获取对象
        Student student = applicationContext.getBean("student",Student.class);

        System.out.println(student);
    }
}
