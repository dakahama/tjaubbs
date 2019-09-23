package com.tjau.bbs.tjaubbs.controller;

import com.github.pagehelper.PageInfo;
import com.tjau.bbs.tjaubbs.domain.Contract;
import com.tjau.bbs.tjaubbs.domain.Task;
import com.tjau.bbs.tjaubbs.domain.User;
import com.tjau.bbs.tjaubbs.mapper.ContractMapper;
import com.tjau.bbs.tjaubbs.mapper.MessageMapper;
import com.tjau.bbs.tjaubbs.mapper.TaskMapper;
import com.tjau.bbs.tjaubbs.mapper.UserMapper;
import com.tjau.bbs.tjaubbs.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ContractController {

    @Autowired
    ContractService contractService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    TaskMapper taskMapper;
    @Autowired
    ContractMapper contractMapper;

    @Autowired
    MessageMapper messageMapper;

    @RequestMapping("/getAllContracts")
    @ResponseBody
    public Object getAllContracts(HttpServletRequest request,@RequestParam Map paramMap){
        User userA = (User) request.getSession().getAttribute("user");
        paramMap.put("userAId",userA.getId());
        PageInfo pageInfo = contractService.getAllContracts(paramMap);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",pageInfo.getList());
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",pageInfo.getTotal());
        return map;
    }

    @RequestMapping("/getAllContracts2")
    @ResponseBody
    public Object getAllContracts2(HttpServletRequest request,@RequestParam Map paramMap){
        User userB = (User) request.getSession().getAttribute("user");
        paramMap.put("userBId",userB.getId());
        PageInfo pageInfo = contractService.getAllContracts2(paramMap);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",pageInfo.getList());
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",pageInfo.getTotal());
        return map;
    }

    //得到老师正在进行的合同
    @RequestMapping("/getTeaIngContracts")
    @ResponseBody
    public Object getTeaIngContracts(HttpServletRequest request,@RequestParam Map paramMap){
        User userB = (User) request.getSession().getAttribute("user");
        paramMap.put("userBId",userB.getId());
        PageInfo pageInfo = contractService.getTeaIngContracts(paramMap);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",pageInfo.getList());
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",pageInfo.getTotal());
        return map;
    }



    @PostMapping("/addContract")
    @ResponseBody
    public String addContract(@RequestParam Map map,HttpServletRequest request){
        User userB = (User) request.getSession().getAttribute("user");
        Contract contract = new Contract();
        contract.setUserAId((String) map.get("userAId"));
        User userA = userMapper.getUserById((String) map.get("userAId"));
        contract.setUserA(userA);
        contract.setUserBId(userB.getId());
        contract.setUserB(userB);

        int time = (int) System.currentTimeMillis();
        String id = String.valueOf(time);
        contract.setId(id);

        String taskId = (String) map.get("taskId");
        Task task = taskMapper.getTaskById(taskId);
        contract.setTask(task);
        contract.setTaskId(taskId);
        contract.setTaskTitle(task.getTitle());

        contract.setState(1);

        if (contractMapper.addContract(contract)){
            messageMapper.changeMessageState(2, (String) map.get("id"));

            //将项目的状态改变 将消息统一处理
            taskMapper.changeTaskState(3,taskId);
            //
            messageMapper.setMessageStateByTaskId(taskId);
            return "success";
        }
        return "fail";
    }


    // 改变合同的状态
    @PostMapping("/changeContractState")
    @ResponseBody
    public String changeContractstate(@RequestParam Map map,HttpServletRequest request){
        if (contractMapper.changeStateByIdAndState(map)){
            return "success";
        }else {
            return "fail";
        }
    }

    /*
    将项目的合同设置为完成状态 同时 任务的状态也需要改变
    */
    @PostMapping("/finishContract")
    @ResponseBody
    public String finishContract(@RequestParam Map map,HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        if (user.getShenfen()!=1){
            return "fail";
        }

        if (contractMapper.changeStateByIdAndState(map)){

            String contractId = (String) map.get("contractId");
            Contract contract = (Contract) contractMapper.getContractById(contractId);
            String taskId = contract.getTaskId();
            taskMapper.changeTaskState(4,taskId);

        }
        return "success";
    }

    @PostMapping("/cancelContract")
    @ResponseBody
    public String cancelContract(@RequestParam Map map,HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        if (user.getShenfen()!=1){
            return "fail";
        }

        if (contractMapper.changeStateByIdAndState(map)){

            String contractId = (String) map.get("contractId");
            Contract contract = (Contract) contractMapper.getContractById(contractId);
            String taskId = contract.getTaskId();
            taskMapper.changeTaskState(2,taskId);

        }
        return "success";
    }

}
