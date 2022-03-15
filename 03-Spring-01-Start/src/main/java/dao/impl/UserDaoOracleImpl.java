package dao.impl;

import dao.UserDao;

public class UserDaoOracleImpl implements UserDao {
    @Override
    public void findAll() {
        System.out.println("使用Oracle数据库");
    }
}
