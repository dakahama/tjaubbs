package com.tjau.bbs.tjaubbs.mapper;

import com.tjau.bbs.tjaubbs.domain.Contract;
import com.tjau.bbs.tjaubbs.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ContractMapper {

    @Insert("insert into t_contract(id,taskId,taskTitle,userAId,userBId,state) values("+
            "#{id},#{taskId},#{taskTitle},#{userAId},#{userBId},#{state})")
    public boolean addContract(Contract contract);

    @Select("select * from t_contract where userAid = #{id}")
    public List<Contract> getAllContractsById(String id);

    @Select("select * from t_contract where userBid = #{id} and state = 2")
    public List<Contract> getAllContractsById2(String id);

    @Select("select * from t_contract where userAid = ${id} and state = ${state}")
    public Contract getContractsByIdAndState(@Param("id") String id,@Param("state") int state);

    @Select("select * from t_contract where userBid = ${id} and state = ${state}")
    public List<Contract> getContractsByIdAndState2(@Param("id") String id,@Param("state") int state);

    @Select("select * from t_contract where userAId = ${userId} and state= ${state}")
    public List<Contract> checkWithUseridAndState(@Param("userId") String userId,@Param("state") int state);

    @Update("update t_contract set state=#{state} where id = #{contractId}")
    public boolean changeStateByIdAndState(Map map);



    @Select("select * from t_contract where taskId = ${taskId} and state = ${state}")
    public Contract getContractByTaskIdAndState
            (@Param("taskId") String taskId,@Param("state") int state);

    @Select("select * from t_contract where id = ${id}")
    public Contract getContractById
            (@Param("id") String id);


}
