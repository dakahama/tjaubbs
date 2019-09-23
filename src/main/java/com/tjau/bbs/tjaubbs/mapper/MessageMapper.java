package com.tjau.bbs.tjaubbs.mapper;

import com.tjau.bbs.tjaubbs.domain.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface MessageMapper {




    //从消息表中找出 学生的信息
    @Select("select * from t_message where userAId = ${id} and type = ${type} and state = ${state}")
    public Message getMessageByTypeAndState(@Param("id") String userAId,@Param("type") int type,@Param("state") int state);

    @Insert("insert into t_message(id,userAId,userBId,taskId,detail,type,state)"+
            "values(#{id},#{userAId},#{userBId},#{taskId},#{detail},#{type},#{state})")
    public boolean addMessage(Message message);
    @Insert("insert into t_message(id,userAId,userBId,taskId,detail,type,state)"+
            "values(#{id},#{userAId},#{userBId},#{taskId},#{detail},#{type},#{state})")
    public boolean addMessageByMap(Map map);


    //改变消息的状态
    @Update("update t_message set state = ${state} where id = ${messageid}")
    public boolean changeMessageState(@Param("state") int state,@Param("messageid") String id);

    //得到一位教师所有申请信息
    @Select("select * from t_message where userBId = ${userBId} and state = ${state}")
    public List<Message> getApplyMessages(@Param("userBId") String userBId,@Param("state") int state);

    //得到所有没有处理的申请信息
    @Select("select * from t_message where taskId = #{taskid} and state = 1 and type = 1")
    public List<Message> getMessagesByTaskId(String taskid);

    //得到所有申请取消合同的消息
    @Select("select * from t_message where taskId = #{taskid} and state = 1 and type = 2")
    public List<Message> getCancelMessageByContractId(String taskid);

    //得到所有申请取消合同的消息
    @Select("select * from t_message where taskId = #{taskid} and state = 1 and type = 3")
    public List<Message> getFinishMessageByContractId(String taskid);


    //将剩下的申请信息 状态设置为4 拒绝
    @Update("update t_message set state = 4 where taskId = #{taskid} and " +
            "type = 1 and state = 1")
    public boolean setMessageStateByTaskId(String taskid);

}
