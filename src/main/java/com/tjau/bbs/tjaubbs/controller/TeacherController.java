package com.tjau.bbs.tjaubbs.controller;

import com.github.pagehelper.PageInfo;
import com.tjau.bbs.tjaubbs.domain.Contract;
import com.tjau.bbs.tjaubbs.domain.Message;
import com.tjau.bbs.tjaubbs.domain.Task;
import com.tjau.bbs.tjaubbs.domain.User;
import com.tjau.bbs.tjaubbs.mapper.ContractMapper;
import com.tjau.bbs.tjaubbs.mapper.MessageMapper;
import com.tjau.bbs.tjaubbs.mapper.TaskMapper;
import com.tjau.bbs.tjaubbs.mapper.UserMapper;
import com.tjau.bbs.tjaubbs.service.MessageService;
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
public class TeacherController {

    @Autowired
    TaskMapper taskMapper;
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    MessageService messageService;
    @Autowired
    ContractMapper contractMapper;
    @Autowired
    UserMapper userMapper;

    //跳转到教师后台页面
    @RequestMapping("/manager_tea")
    public ModelAndView tomanager_tea(HttpServletRequest request){
        return new ModelAndView("user/manager_tea");
    }

    // 跳转到申请消息
    @RequestMapping("/msgteaapply")
    public ModelAndView msgteapply(HttpServletRequest request){
        return new ModelAndView("message/msg_tea_apply");
    }

    // 得到取消合同的信息
    /*
    @RequestMapping("/msgteaover")
    public ModelAndView msgteover(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("message/msg_tea_cancel");
        //先从合同表中 找出该老师有没有正在进行的项目 最多只有一个
        User userB = (User) request.getSession().getAttribute("user");
        Contract contract = contractMapper.getContractsByIdAndState2(userB.getId(),1);
        if (contract!=null){
            //有正在发布的项目
            modelAndView.addObject("have",true);
            contract.setUserB(userB);
            modelAndView.addObject("contract",contract);

        }else {
            modelAndView.addObject("have",false);
        }
        return modelAndView;
    }
    */
    /*
    @RequestMapping("/msgteafinish")
    public ModelAndView msgtefinish(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("message/msg_tea_finish");
        //先从合同表中 找出该老师有没有正在进行的项目 最多只有一个
        User userB = (User) request.getSession().getAttribute("user");
        Contract contract = contractMapper.getContractsByIdAndState2(userB.getId(),1);
        if (contract!=null){
            //有正在发布的项目
            modelAndView.addObject("have",true);
            contract.setUserB(userB);
            modelAndView.addObject("contract",contract);

        }else {
            modelAndView.addObject("have",false);
        }
        return modelAndView;
    }

    */
}
