package com.tjau.bbs.tjaubbs.controller;

import com.github.pagehelper.PageInfo;
import com.tjau.bbs.tjaubbs.domain.Message;
import com.tjau.bbs.tjaubbs.domain.User;
import com.tjau.bbs.tjaubbs.mapper.ContractMapper;
import com.tjau.bbs.tjaubbs.mapper.MessageMapper;
import com.tjau.bbs.tjaubbs.mapper.TaskMapper;
import com.tjau.bbs.tjaubbs.mapper.UserMapper;
import com.tjau.bbs.tjaubbs.service.MessageService;
import com.tjau.bbs.tjaubbs.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MessageController {
    @Autowired
    TaskMapper taskMapper;
    @Autowired
    ContractMapper contractMapper;
    @Autowired
    MessageMapper messageMapper;

    @Autowired
    UserMapper userMapper;
    @Autowired
    MessageService messageService;

    @Autowired
    PageService pageService;

    @PostMapping("/addMessage")
    @ResponseBody
    public String adMessage(@RequestParam Map map,HttpServletRequest request){
        User userA = (User) request.getSession().getAttribute("user");
        map.put("userAId",userA.getId());
        int id = (int) System.currentTimeMillis();
        map.put("id",String.valueOf(id));

        boolean b = messageMapper.addMessageByMap(map);
        if (b){
            return "success";
        }else {
            return "fail";
        }
    }

    @PostMapping("/changeMessageState")
    @ResponseBody
    public String cancelApply(@RequestParam Map map, HttpServletRequest request){
        String messageid = (String) map.get("messageid");
        int state = Integer.parseInt((String) map.get("state"));
        //int type = Integer.parseInt((String) map.get("type"));
        boolean b = messageMapper.changeMessageState(state,messageid);
        if (b){
            return "success";
        }else {
            return "fail";
        }
    }

    @RequestMapping("/applyTask")
    public ModelAndView applyTask(@RequestParam Map map, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("result");
        User user = (User) request.getSession().getAttribute("user");
        String taskid = (String) map.get("taskid");
        String taskuserid = (String) map.get("taskuserid");
        String detail = (String) map.get("detail");
        //有没有登入
        if (user == null) {
            modelAndView.addObject("msg", "请先登入");
        } else {
            if (user.getShenfen()==0){
                //有没有正在进行的项目
                if (messageMapper.getMessageByTypeAndState(user.getId(),1,1)!=null) {
                    modelAndView.addObject("msg", "您有正在申请的项目,不可同时申请多个项目");
                }
                else if (contractMapper.checkWithUseridAndState(user.getId(),1).size()!=0){
                    modelAndView.addObject("msg", "您有正在进行的项目,不可同时进行多个项目");
                }
                else {
                    //在数据库中生成一个对应的申请信息
                    Message message = new Message();
                    int time = (int) System.currentTimeMillis();
                    String id = String.valueOf(time);
                    message.setId(id);
                    message.setUserAId(user.getId());
                    message.setUserBId(taskuserid);
                    message.setTaskId(taskid);
                    message.setDetail(detail);
                    message.setType(1);
                    message.setState(1);
                    if (messageMapper.addMessage(message)) {
                        modelAndView.addObject("msg","申请成功");
                    }
                }
            }else {
                modelAndView.addObject("msg", "请登入学生账号");
            }

        }
        //有没有申请过 在message表中查
        return modelAndView;
    }

    @RequestMapping("/getApplyMessages")
    @ResponseBody
    public Map getApplyMessages(HttpServletRequest request, @RequestParam Map paramMap){
        User user = (User) request.getSession().getAttribute("user");
        String userId = user.getId();

        paramMap.put("userId",userId);
        paramMap.put("state",1);
        PageInfo pageInfo = messageService.getApplyMessages(request,paramMap);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",pageInfo.getList());
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",pageInfo.getTotal());
        return map;

    }
    @PostMapping("/getCancelMessages")
    public Map getCancelMessages(HttpServletRequest request, @RequestParam Map paramMap){
        PageInfo pageInfo = messageService.getCancelMessagesByContracl(paramMap);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",pageInfo.getList());
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",pageInfo.getTotal());
        return map;
    }

    @PostMapping("/getAllFinishMessages")
    public Map getFinishMessages(HttpServletRequest request, @RequestParam Map paramMap){
        PageInfo pageInfo = messageService.getFinishMessagesByContracl(paramMap);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",pageInfo.getList());
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",pageInfo.getTotal());
        return map;
    }

}
