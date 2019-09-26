package com.st.dream.dao;

import com.st.dream.pojo.UserAAA;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    @Select("SELECT * FROM user where username = #{name}")
    UserAAA findUserByName(@Param("name") String name);
}
