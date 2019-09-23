package com.tjau.bbs.tjaubbs.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tjau.bbs.tjaubbs.domain.Task;
import com.tjau.bbs.tjaubbs.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {

    @Autowired
    TaskMapper taskMapper;

    /**
     *  得到所有的任务
     * @param paramMap page limit
     * @return pageinfo
     */
    public PageInfo<Task> getAllTasks(Map<String,String> paramMap){
        int page = Integer.parseInt(paramMap.get("curr"));
        int limit = Integer.parseInt(paramMap.get("limit"));

        PageHelper.startPage(page,limit);
        List<Task> list = taskMapper.getAllTask();
        return new PageInfo<Task>(list);
    }

}
