package com.tjau.bbs.tjaubbs.controller;

import com.github.pagehelper.PageInfo;
import com.tjau.bbs.tjaubbs.domain.Contract;
import com.tjau.bbs.tjaubbs.domain.Process;
import com.tjau.bbs.tjaubbs.domain.Task;
import com.tjau.bbs.tjaubbs.domain.User;
import com.tjau.bbs.tjaubbs.mapper.ContractMapper;
import com.tjau.bbs.tjaubbs.mapper.ProcessMapper;
import com.tjau.bbs.tjaubbs.mapper.TaskMapper;
import com.tjau.bbs.tjaubbs.mapper.UserMapper;
import com.tjau.bbs.tjaubbs.service.PageService;
import com.tjau.bbs.tjaubbs.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class TaskController {

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    PageService pageService;
    @Autowired
    TaskService taskService;
    @Autowired
    UserMapper userMapper;

    @Autowired
    ContractMapper contractMapper;

    @Autowired
    ProcessMapper processMapper;
    /**
     * 用来跳转到主页 主页就是显示所有的任务
     * @return index.html
     */
    @RequestMapping("/")
    public ModelAndView toIndex(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping("/ShowTaskDetail")
    public ModelAndView totaskdetail(@RequestParam("id") String id){
        Task task = taskMapper.getTaskById(id);
        User user = userMapper.getUserById(task.getUserId());
        task.setUser(user);
        ModelAndView modelAndView = new ModelAndView("taskdetail");
        modelAndView.addObject("task",task);

        if (task.getState() != 2){
            // 找到该任务的合同
            if (task.getState() == 3){
                //如果项目正在进行
                Contract contract = contractMapper.getContractByTaskIdAndState(task.getId(),1);
                String contractId = contract.getId();

                List<Process> processes = processMapper.getAllProcesses(contractId);
                modelAndView.addObject("processes",processes);
            }
            if (task.getState() == 4){
                //如果项目已经结束
                Contract contract = contractMapper.getContractByTaskIdAndState(task.getId(),2);
                String contractId = contract.getId();

                List<Process> processes = processMapper.getAllProcesses(contractId);
                modelAndView.addObject("processes",processes);
            }

        }else {
            modelAndView.addObject("processes",null);
        }

        return modelAndView;

    }

    @RequestMapping("/getAllTasks")
    @ResponseBody
    public Object getalltasks(@RequestParam Map<String,String> paramMap){

        PageInfo<Task> pageInfo = taskService.getAllTasks(paramMap);
        Map<String,Object> map = new HashMap<String,Object>();
        List<Task> list = pageInfo.getList();
        for (Task task : list) {
            User user = userMapper.getUserById(task.getUserId());
            task.setUser(user);
        }
        map.put("data",pageInfo.getList());
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",pageInfo.getTotal());
        return map;
    }

    // 得到未发布的项目
    @RequestMapping("/showUnSaveTasks")
    @ResponseBody
    public Object showunsavedtasks(HttpServletRequest request, @RequestParam Map<String,String> paramMap){
        User user = (User) request.getSession().getAttribute("user");
        String userId = user.getId();
        paramMap.put("userId",userId);
        PageInfo pageInfo = pageService.getUnsavedTasks(request,paramMap);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",pageInfo.getList());
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",pageInfo.getTotal());
        return map;
    }

    // 得到正在等待 签约的项目
    @RequestMapping("/showWaitTasks")
    @ResponseBody
    public Object showWaittasks(HttpServletRequest request, @RequestParam Map<String,String> paramMap){
        User user = (User) request.getSession().getAttribute("user");
        String userId = user.getId();
        paramMap.put("userId",userId);
        paramMap.put("state","2");
        PageInfo pageInfo = pageService.getTasksByState(request,paramMap);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",pageInfo.getList());
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",pageInfo.getTotal());
        return map;
    }


    // 发布项目
    @PostMapping("/publicTask")
    @ResponseBody
    public String publictask(@RequestParam Map<String,String> paramMap,HttpServletRequest request){
        String id = paramMap.get("id");
        User userB = (User) request.getSession().getAttribute("user");
        Task task = taskMapper.getPublicedTask(userB.getId());
        if (task == null){
            // 一个老师同一时间只能发布一个项目
            if (taskMapper.changeTaskState(2,id)){
                return "项目发布成功";
            }else {
                return "项目发布失败";
            }
        }else {
            return "您已经有正在发布的项目了";
        }
    }

    // 存为草稿
    @PostMapping("/caogaoTask")
    @ResponseBody
    public String caogaotask(@RequestParam Map<String,String> paramMap,HttpServletRequest request){
        String id = paramMap.get("id");
        User userB = (User) request.getSession().getAttribute("user");
        if (taskMapper.changeTaskState(1,id)){
            return "项目发布成功";
        }else {
            return "项目发布失败";
        }
    }



    @GetMapping("/editTaskPage")
    public ModelAndView editTask(@RequestParam Map<String,String> paramMap,HttpServletRequest request){
        String id = paramMap.get("id");
        ModelAndView modelAndView = new ModelAndView("edittask");
        Task task = taskMapper.getTaskById(id);
        Date date =  task.getEndDate();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        modelAndView.addObject("task",task);
        modelAndView.addObject("endDate",dateString);
        System.out.println(task.getTitle());
        return modelAndView;
    }


    // 跳转到添加项目页面
    @RequestMapping("/toAddTask")
    public String toaddTask(){
        return "addtask";
    }

    // 添加项目
    @RequestMapping("/addTask")
    public ModelAndView addTask(@RequestParam Map paramMap, HttpServletRequest request) throws ParseException {
        ModelAndView modelAndView = new ModelAndView("result");
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            modelAndView.addObject("msg","请先登入");
        }else {
            Task task = new Task();
            int time = (int) System.currentTimeMillis();
            String id = String.valueOf(time);
            String userId = user.getId();
            Date publicDate = new Date();
            String title = (String) paramMap.get("title");
            String keyword = (String) paramMap.get("keyword");
            String endtime = (String) paramMap.get("endTime");
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endtime);
            String detail = (String) paramMap.get("my-editormd-html-code");
            int money = Integer.parseInt((String) paramMap.get("money"));

            task.setId(id);
            task.setDetail(detail);
            task.setEndDate(endDate);
            task.setKeyword(keyword);
            task.setPublicDate(publicDate);
            task.setMoney(money);
            task.setDetail(detail);
            task.setTitle(title);
            task.setUserId(userId);

            String statestr = (String) paramMap.get("state");
            if (statestr == null || statestr.equals("立即发布")){
                task.setState(2);
            }else {
                task.setState(1);
            }
            if(taskMapper.addTask(task)){
                modelAndView.addObject("msg","添加成功");
            }
        }
        return modelAndView;
    }
    // 编辑项目
    @RequestMapping("/editTask")
    public ModelAndView updateTask(@RequestParam Map paramMap, HttpServletRequest request) throws ParseException {
        ModelAndView modelAndView = new ModelAndView("result");
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            modelAndView.addObject("msg","请先登入");
            modelAndView.setViewName("result");
        }else {
            Task task = new Task();
            Date publicDate = new Date();
            String title = (String) paramMap.get("title");
            String keyword = (String) paramMap.get("keyword");
            String time = (String) paramMap.get("endTime");
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(time);
            String detail = (String) paramMap.get("my-editormd-html-code");
            int money = Integer.parseInt((String) paramMap.get("money"));
            task.setDetail(detail);
            task.setEndDate(endDate);
            task.setKeyword(keyword);
            task.setPublicDate(publicDate);
            task.setMoney(money);
            task.setDetail(detail);
            task.setTitle(title);
            task.setId((String) paramMap.get("taskid"));
            String statestr = (String) paramMap.get("state");
            if (statestr.equals("存为草稿")){
                task.setState(1);
            }else {
                task.setState(2);
            }
            if(taskMapper.editTask(task)){
                modelAndView.addObject("msg","上传成功");
            }else {
                modelAndView.addObject("msg","上传失败");
            }
        }
        return modelAndView;
    }

    //删除项目
    @PostMapping("/deleteTask")
    @ResponseBody
    public String deleteTask(@RequestParam Map paramMap, HttpServletRequest request){
        String id = (String) paramMap.get("id");
        if(taskMapper.deleteTask(id)){
            return "删除成功";
        }else {
            return "删除失败";
        }
    }
}
