package com.tjau.bbs.tjaubbs.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tjau.bbs.tjaubbs.domain.Contract;
import com.tjau.bbs.tjaubbs.domain.Process;
import com.tjau.bbs.tjaubbs.mapper.ContractMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ContractService {

    @Autowired
    ContractMapper contractMapper;

    public PageInfo<Contract> getAllContracts(Map<String,String> paramMap){
        int page = Integer.parseInt(paramMap.get("page"));
        int limit = Integer.parseInt(paramMap.get("limit"));
        String userAId = paramMap.get("userAId");
        PageHelper.startPage(page,limit);
        List<Contract> list = contractMapper.getAllContractsById(userAId);
        return new PageInfo<Contract>(list);
    }

    public PageInfo<Contract> getTeaIngContracts(Map<String,String> paramMap){
        int page = Integer.parseInt(paramMap.get("page"));
        int limit = Integer.parseInt(paramMap.get("limit"));
        String userBId = paramMap.get("userBId");
        PageHelper.startPage(page,limit);
        List<Contract> list = contractMapper.getContractsByIdAndState2(userBId,1);
        return new PageInfo<Contract>(list);
    }


    public PageInfo<Contract> getAllContracts2(Map<String,String> paramMap){
        int page = Integer.parseInt(paramMap.get("page"));
        int limit = Integer.parseInt(paramMap.get("limit"));
        String userBId = paramMap.get("userBId");
        PageHelper.startPage(page,limit);
        List<Contract> list = contractMapper.getAllContractsById2(userBId);
        return new PageInfo<Contract>(list);
    }

}
