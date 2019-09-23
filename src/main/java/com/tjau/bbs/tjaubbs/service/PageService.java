package com.tjau.bbs.tjaubbs.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tjau.bbs.tjaubbs.domain.Task;
import com.tjau.bbs.tjaubbs.mapper.TaskMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class PageService {


    @Autowired
    TaskMapper taskMapper;

    public PageInfo<Task> getUnsavedTasks(HttpServletRequest request, Map<String,String> paramMap){
        Integer page = Integer.parseInt(paramMap.get("page"));
        Integer limit = Integer.parseInt(paramMap.get("limit"));
        String userId = paramMap.get("userId");

        PageHelper.startPage(page,limit);
        List<Task> list = taskMapper.getUnSavedTasksById(userId);
        //System.out.println(list.size());
        PageInfo<Task> pageInfo = new PageInfo<Task>(list);
        
        return pageInfo;
    }

    public PageInfo<Task> getTasksByState(HttpServletRequest request, Map<String,String> paramMap){
        int page = Integer.parseInt(paramMap.get("page"));
        int limit = Integer.parseInt(paramMap.get("limit"));
        String  userId = paramMap.get("userId");
        int state = Integer.parseInt(paramMap.get("state"));
        PageHelper.startPage(page,limit);
        List<Task> list = taskMapper.getTasksByState(userId,state);
        System.out.println(list.size());
        PageInfo<Task> pageInfo = new PageInfo<Task>(list);
        return pageInfo;
    }





}
