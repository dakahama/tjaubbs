package com.tjau.bbs.tjaubbs.mapper;

import com.tjau.bbs.tjaubbs.domain.Task;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Mapper
public interface TaskMapper {

    // 得到所有的任务
    @Select("select * from t_task where state != 1")
    public List<Task> getAllTask();

    @Insert("insert into t_task(id,userId,publicDate,endDate,title,keyword,detail,money,state) "+
            "values(#{id},#{userId},#{publicDate},#{endDate},#{title},#{keyword},#{detail},#{money},#{state})")
    public boolean addTask(Task task);

    // 找到老师相应的 未发布的项目
    @Select("select * from t_task where userid = #{userId} and state = 1")
    public List<Task> getUnSavedTasksById(String userId);

    @Select("select * from t_task where userid = #{userId} and state = 2")
    public Task getPublicedTask(String userId);

    @Select("select * from t_task where userid = ${userId} and state = ${state}")
    public List<Task> getTasksByState(@Param("userId") String userId,@Param("state") int state);


    @Select("select * from t_task where id = ${id}")
    public Task getTaskById(@Param("id") String id);


    @Update("update t_task set state = ${state} where id = ${id}")
    public boolean changeTaskState(@Param("state") int state, @Param("id") String id);

    @Update("update t_task set publicDate=#{publicDate},endDate=#{endDate},title=#{title},keyword=#{keyword},detail=#{detail},money=#{money},state=#{state} where id=#{id}")
    public boolean editTask(Task task);

    @Delete("delete from t_task where id=${id}")
    public boolean deleteTask(@Param("id") String id);


}
