package com.tjau.bbs.tjaubbs.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tjau.bbs.tjaubbs.domain.Message;
import com.tjau.bbs.tjaubbs.domain.Task;
import com.tjau.bbs.tjaubbs.mapper.MessageMapper;
import com.tjau.bbs.tjaubbs.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {

    @Autowired
    MessageService messageService;
    @Autowired
    TaskMapper taskMapper;
    @Autowired
    MessageMapper messageMapper;
    /*
    public PageInfo<Message> getTasksByState(HttpServletRequest request, Map<String,String> paramMap){
        Integer page = Integer.parseInt(paramMap.get("page"));
        Integer limit = Integer.parseInt(paramMap.get("limit"));
        String  userId = paramMap.get("userId");
        int state = Integer.parseInt(paramMap.get("state"));
        PageHelper.startPage(page,limit);
        List<Task> list = taskMapper.getTasksByState(userId,state);
        System.out.println(list.size());
        PageInfo<Message> pageInfo;
        pageInfo = new PageInfo<Message>(list);
        return pageInfo;
    }
    */

    public PageInfo<Message> getMessagesByIngTask(Map<String,String> paramMap){
        int page = Integer.parseInt(paramMap.get("page"));
        int limit = Integer.parseInt(paramMap.get("limit"));
        String taskId = paramMap.get("taskId");
        PageHelper.startPage(page,limit);
        List<Message> list = messageMapper.getMessagesByTaskId(taskId);
        return new PageInfo<Message>(list);
    }

    public PageInfo<Message> getCancelMessagesByContracl(Map<String,String> paramMap){
        int page = Integer.parseInt(paramMap.get("page"));
        int limit = Integer.parseInt(paramMap.get("limit"));
        String contractId = paramMap.get("contractId");
        PageHelper.startPage(page,limit);
        List<Message> list = messageMapper.getCancelMessageByContractId(contractId);
        System.out.println(list.size());
        return new PageInfo<Message>(list);
    }

    public PageInfo<Message> getFinishMessagesByContracl(Map<String,String> paramMap){
        int page = Integer.parseInt(paramMap.get("page"));
        int limit = Integer.parseInt(paramMap.get("limit"));
        String contractId = paramMap.get("contractId");
        PageHelper.startPage(page,limit);
        List<Message> list = messageMapper.getFinishMessageByContractId(contractId);
        return new PageInfo<Message>(list);
    }

    // 得到申请消息
    public PageInfo<Message> getApplyMessages(HttpServletRequest request, Map<String,String> paramMap){
        int page = Integer.parseInt(paramMap.get("page"));
        int limit = Integer.parseInt(paramMap.get("limit"));
        String  userBId = paramMap.get("userId");
        PageHelper.startPage(page,limit);
        List<Message> list = messageMapper.getApplyMessages(userBId,1);
        System.out.println(list.size());
        PageInfo<Message> pageInfo = new PageInfo<Message>(list);
        return pageInfo;
    }


}
