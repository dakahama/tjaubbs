package com.tjau.bbs.tjaubbs.controller;

import com.github.pagehelper.PageInfo;
import com.tjau.bbs.tjaubbs.domain.User;
import com.tjau.bbs.tjaubbs.mapper.ProcessMapper;
import com.tjau.bbs.tjaubbs.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ProcessController {
    @Autowired
    ProcessService processService;
    @Autowired
    ProcessMapper processMapper;

    @RequestMapping("/getAllprocess")
    @ResponseBody
    public Object showIngtasks(HttpServletRequest request,@RequestParam Map paramMap,@RequestParam("contractId") String contractId){

        paramMap.put("contractId",contractId);
        PageInfo pageInfo = processService.getAllProcesses(paramMap);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",pageInfo.getList());
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",pageInfo.getTotal());
        return map;
    }


    @RequestMapping("/deleteProcess")
    @ResponseBody
    public Object showIngtasks(HttpServletRequest request,@RequestParam("id") String contractId){
        if(processMapper.deleteProcessById(contractId)){
            return "删除成功";
        }else {
            return "删除失败";
        }
    }

    @RequestMapping("/showProcess")
    public ModelAndView showProcessStu(@RequestParam("id") String contractId){
        return new ModelAndView("showprocess").addObject("contractId",contractId);
    }



}
