package dao.impl;

import dao.UserDao;

public class UserDaoMySQLImpl implements UserDao {
    @Override
    public void findAll() {
        System.out.println("使用MySQL");
    }
}
