package com.st.dream.service;


import com.st.dream.pojo.UserAAA;
import com.st.dream.sbmybatis.model.User;

public interface UserService {

    UserAAA findUserByName(String name);

    User getUserByName(String name);

    User selectByPrimaryKey(Integer id);

    Integer deleteByPrimaryKey(Integer id);

    User getUserWithProperty(Integer id,  Integer[] propertyIds);

    Integer addUser(User user);

    void deletePropertyByIds(Integer[] ids);
}
