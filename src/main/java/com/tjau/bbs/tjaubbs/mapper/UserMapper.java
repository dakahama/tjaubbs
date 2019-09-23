package com.tjau.bbs.tjaubbs.mapper;

import com.tjau.bbs.tjaubbs.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.Map;

@Mapper
public interface UserMapper {


    @Select("select * from t_user where id = #{id}")
    public User getUserById(String id);


    @Insert("insert into t_user(id,realname,email,username,college,major,sign,registerTime,password,gender,shenfen) values("
            +"#{id},#{realname},#{email},#{username},#{college},#{major},#{sign},#{registerTime},#{password},#{gender},#{shenfen})")
    public boolean register(User student);

    //修改个人信息
    @Update("update t_user set username=#{username},email=#{email},gender=#{gender},college=#{college},major=#{major},sign=#{sign} "+
            "where id = #{id}")
    public boolean changeInfo(Map map);

    @Update("update t_user set resume = '${resume}' where id = ${id}")
    public boolean addOrChangeRuseme(@Param("resume") String resume,@Param("id") String id);

    @Update("update t_user set password = ${password} where id = ${id}")
    public boolean changePassword(@Param("password") String password,@Param("id") String id);

    @Update("update t_user set avatar = '${avatar}' where id = ${id}")
    public boolean changeAvatar(@Param("avatar") String avatar,@Param("id") String id);


}
