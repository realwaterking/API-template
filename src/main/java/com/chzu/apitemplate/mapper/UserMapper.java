package com.chzu.apitemplate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chzu.apitemplate.model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM user WHERE phone = #{phone}")
    User getByPhoneNumber(@Param("phone") String phone);

    @Update("update user set password = #{password},update_time = now() where phone = #{phone}")
    void update(@Param("password") String password, @Param("phone") String phone);

    @Update("update user set is_delete = 1,update_time = now() where phone = #{phone}")
    boolean logicDelete(@Param("phone") String phone);

//    User getByOpenid(String openid);

//    List<User> selectAll();
}
