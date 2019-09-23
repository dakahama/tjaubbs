package com.tjau.bbs.tjaubbs.mapper;

import com.tjau.bbs.tjaubbs.domain.Contract;
import com.tjau.bbs.tjaubbs.domain.Process;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface ProcessMapper {

    @Insert("insert into t_process(id,contractId,title,`describe`,file,publicDate)"+
            " values(#{id},#{contractId},#{title},#{describe},#{file},#{publicDate})")
    public boolean addProcess(Process process);

    @Select("select * from t_process where contractId = #{contractId}")
    public List<Process> getAllProcesses(String contractId);

    @Delete("delete from t_process where id = #{id}")
    public boolean deleteProcessById(String id);

}
