package com.fc.dao.impl;

import com.fc.dao.UserDao;
import com.fc.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
@Override
    public List<User> findAll(){
    //数据库连接
    ArrayList<User> users = new ArrayList<>();

    users.add(new User(1, "玛卡巴卡", "123456"));
    users.add(new User(2, "汤不里不", "666666"));
    users.add(new User(3, "依古比古", "888888"));

    return users;
}

}
