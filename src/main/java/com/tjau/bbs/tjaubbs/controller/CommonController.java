package com.tjau.bbs.tjaubbs.controller;

import com.alibaba.druid.sql.ast.statement.SQLForeignKeyImpl;
import com.tjau.bbs.tjaubbs.domain.Contract;
import com.tjau.bbs.tjaubbs.domain.User;
import com.tjau.bbs.tjaubbs.mapper.ContractMapper;
import com.tjau.bbs.tjaubbs.mapper.TaskMapper;
import com.tjau.bbs.tjaubbs.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommonController {




    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ContractMapper contractMapper;


    @RequestMapping("/toAddTaskPage")
    public String toAddTask(){
        return "addtask";
    }


    @RequestMapping("/toManager")
    public String toManageTea(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        int shenfen = user.getShenfen();
        if (shenfen==0) return "redirect:/manager_stu";
        return "redirect:/manager_tea";
    }

    @RequestMapping("/toBasicInfo")
    public String tobasicInfo(HttpServletRequest request) {
        return "user/editbasic";
    }


    @RequestMapping("/toEditResume")
    public String toeditresume(HttpServletRequest request){
            return "user/editresume";
    }
    @RequestMapping("/toEditAvatar")
    public String toeditavatar(HttpServletRequest request){
        return "user/editavatar";
    }
    @RequestMapping("/toMyResume")
    public String tomyresume(){
        return "showresume";
    }



    @RequestMapping("/toTeaTaskUnSaved")
    public String toteataskunsaved(){
        return "user/teatask_unsaved";
    }

    @RequestMapping("/toTeaTaskIng")
    public String toteataskIng(){
        return "user/teatask_ing";
    }

    @RequestMapping("/toTeaTaskWait")
    public String toteataskwait(){
        return "user/teatask_wait";
    }

    @RequestMapping("/toTeaTaskEnd")
    public String toteataskend(){
        return "user/teatask_end";
    }

}
