package com.st.dream.service.impl;

import com.st.dream.dao.UserDao;
import com.st.dream.pojo.UserAAA;
import com.st.dream.sbmybatis.mapper.UserMapper;
import com.st.dream.sbmybatis.model.User;
import com.st.dream.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserAAA findUserByName(String name) {
        return userDao.findUserByName(name);
    }

    @Override
    public User getUserByName(String name) {
        return userMapper.getUserByName(name);
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer deleteByPrimaryKey(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User getUserWithProperty(Integer id, Integer[] propertyIds) {
        return userMapper.getUserWithProperty(id, propertyIds);
    }

    @Override
    @Transactional
    public Integer addUser(User user) {
        userMapper.insertUserXml(user);
//        userMapper.insertUseGeneratedKeys(user);
        System.out.println(user.getId());
        user.getPropertyList().stream().forEach(e -> e.setUserId(user.getId()));
        userMapper.batchInsertProperty(user.getPropertyList());
        return 1;
    }

    @Override
    public void deletePropertyByIds(Integer[] ids) {
        userMapper.deletePropertyByIds(ids);
    }
}
