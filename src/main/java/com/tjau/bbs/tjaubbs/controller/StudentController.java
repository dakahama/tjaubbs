package com.tjau.bbs.tjaubbs.controller;

import com.tjau.bbs.tjaubbs.domain.Contract;
import com.tjau.bbs.tjaubbs.domain.Message;
import com.tjau.bbs.tjaubbs.domain.Task;
import com.tjau.bbs.tjaubbs.domain.User;
import com.tjau.bbs.tjaubbs.mapper.ContractMapper;
import com.tjau.bbs.tjaubbs.mapper.MessageMapper;
import com.tjau.bbs.tjaubbs.mapper.TaskMapper;
import com.tjau.bbs.tjaubbs.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;
import java.util.List;

@Controller
public class StudentController {
    @Autowired
    ContractMapper contractMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    TaskMapper taskMapper;
    @Autowired
    MessageMapper messageMapper;



    @RequestMapping("/manager_stu")
    public ModelAndView tomanager_stu(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("user/manager_stu");
        //先从项目表中 找出该老师有没有正在发布的项目 最多只有一个
        User userB = (User) request.getSession().getAttribute("user");
        Task task = taskMapper.getPublicedTask(userB.getId());
        if (task!=null){
            //有正在发布的项目
            modelAndView.addObject("have",true);
            task.setUser(userB);
            modelAndView.addObject("task",task);

        }else {
            modelAndView.addObject("have",false);
        }
        return modelAndView;
    }

    @RequestMapping("/tomsgstuapply")
    public ModelAndView tomsgstuapply(HttpServletRequest request){
        //转到学生申请信息页面 先从 信息表中找出 未处理的该学生的申请信息
        ModelAndView modelAndView = new ModelAndView("message/msg_stu_apply");
        User userA = (User) request.getSession().getAttribute("user");
        //先从 信息表中找出 未处理的该学生的申请信息 有且只有一个
        Message message = messageMapper.getMessageByTypeAndState(userA.getId(),1,1);
        if (message!=null){
            Task task = taskMapper.getTaskById(message.getTaskId());
            message.setTask(task);
            User userB = userMapper.getUserById(message.getUserBId());
            message.setUserB(userB);
            modelAndView.addObject("have",true);
            modelAndView.addObject("message",message);
            return  modelAndView;
        }else {
            modelAndView.addObject("have",false);
            return  modelAndView;
        }
    }

    // 转到学生用户的解约页面 先要得到正在进行的合同
    @RequestMapping("/tomsgstucancel")
    public ModelAndView tomsgstucancel(HttpServletRequest request){
        User userA = (User) request.getSession().getAttribute("user");
        String userAId = userA.getId();
        ModelAndView modelAndView = new ModelAndView("message/msg_stu_cancel");
        Contract contract = contractMapper.getContractsByIdAndState(userAId,1);
        if (contract!=null){

            User userB = userMapper.getUserById(contract.getUserBId());
            contract.setUserB(userB);
            contract.setUserA(userA);
            Task task = taskMapper.getTaskById(contract.getTaskId());
            contract.setTask(task);
            Message message = messageMapper.getMessageByTypeAndState(userAId,2,1);
            if (message!=null){
                modelAndView.addObject("havemessage",true);
            }else {
                modelAndView.addObject("havemessage",false);
            }
            modelAndView.addObject("have",true);
            modelAndView.addObject("contract",contract);
            return modelAndView;
        }else {
            modelAndView.addObject("have",false);
            return modelAndView;
        }

    }
    //转到学生用户的完成项目页面
    @RequestMapping("/tomsgstufinish")
    public ModelAndView tomsgstufinish(HttpServletRequest request){
        User userA = (User) request.getSession().getAttribute("user");
        String userAId = userA.getId();
        ModelAndView modelAndView = new ModelAndView("message/msg_stu_finish");
        Contract contract = contractMapper.getContractsByIdAndState(userAId,1);
        if (contract!=null){

            User userB = userMapper.getUserById(contract.getUserBId());
            contract.setUserB(userB);
            contract.setUserA(userA);

            Task task = taskMapper.getTaskById(contract.getTaskId());
            contract.setTask(task);
            Message message = messageMapper.getMessageByTypeAndState(userAId,3,1);
            if (message!=null){
                modelAndView.addObject("havemessage",true);
            }else {
                modelAndView.addObject("havemessage",false);
            }
            modelAndView.addObject("have",true);
            modelAndView.addObject("contract",contract);
            return modelAndView;
        }else {
            modelAndView.addObject("have",false);
            return modelAndView;
        }

    }

    @RequestMapping("/stutasking")
    public ModelAndView stutasking(HttpServletRequest request){
        User userA = (User) request.getSession().getAttribute("user");
        String userAId = userA.getId();
        ModelAndView modelAndView = new ModelAndView("user/stutask_ing");
        Contract contract = contractMapper.getContractsByIdAndState(userAId,1);
        if (contract!=null){
            User userB = userMapper.getUserById(contract.getUserBId());
            contract.setUserB(userB);
            contract.setUserA(userA);
            Task task = taskMapper.getTaskById(contract.getTaskId());
            contract.setTask(task);
            modelAndView.addObject("have",true);
            modelAndView.addObject("contract",contract);
            return modelAndView;
        }else {
            modelAndView.addObject("have",false);
            return modelAndView;
        }
    }

    // 跳转到 user/stuask_end
    @RequestMapping("/stutaskend")
    public String stutaskend(HttpServletRequest request){
        return "user/stutask_end";
    }


}
