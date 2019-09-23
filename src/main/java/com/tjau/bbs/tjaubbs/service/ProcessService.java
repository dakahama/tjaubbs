package com.tjau.bbs.tjaubbs.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tjau.bbs.tjaubbs.domain.Process;
import com.tjau.bbs.tjaubbs.mapper.ProcessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProcessService {

    @Autowired
    ProcessMapper processMapper;

    public PageInfo<Process> getAllProcesses(Map<String,String> paramMap){
        int page = Integer.parseInt(paramMap.get("page"));
        int limit = Integer.parseInt(paramMap.get("limit"));
        String contractId = paramMap.get("contractId");
        PageHelper.startPage(page,limit);
        List<Process> list = processMapper.getAllProcesses(contractId);
        return new PageInfo<Process>(list);
    }
}
