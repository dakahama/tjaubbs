package com.tjau.bbs.tjaubbs.controller;

import com.tjau.bbs.tjaubbs.domain.User;
import com.tjau.bbs.tjaubbs.mapper.ContractMapper;
import com.tjau.bbs.tjaubbs.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    ContractMapper contractMapper;

    // 登出
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().setAttribute("user",null);
        return "redirect:/";
    }

    // 修改登入密码
    @PostMapping("/changePassword")
    public ModelAndView changepassword(@RequestParam Map map,HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("result");
        String newpass = (String) map.get("newpassword");
        String repeatpass = (String) map.get("repeatpassword");
        String nowpass = (String) map.get("nowpassword");

        User user = (User) request.getSession().getAttribute("user");
        String oldpass = user.getPassword();

        if (newpass.equals(repeatpass)){

            if (nowpass.equals(oldpass)){
                boolean b = userMapper.changePassword(newpass,user.getId());
                if (b){
                    User newuser = userMapper.getUserById(user.getId());
                    request.getSession().setAttribute("user",newuser);
                    modelAndView.addObject("msg","密码修改成功");
                    return modelAndView;
                }else {
                    modelAndView.addObject("msg","密码修改失败");
                    return modelAndView;
                }
            }else {
                modelAndView.addObject("msg","原密码输入错误");
                return modelAndView;
            }

        }else {
            modelAndView.addObject("msg","两次密码输入不一致");
            return modelAndView;
        }
    }

    // 修改个人信息
    @PostMapping("/changeInfo")
    public ModelAndView changeInfo(@RequestParam Map<String,Object> map,HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("result");
        User user = (User) request.getSession().getAttribute("user");
        int gender = 0;
        if (map.get("gender")!=null){
            gender = Integer.parseInt((String) map.get("gender"));
        }
        map.put("id",user.getId());
        map.put("gender",gender);
        boolean bool = userMapper.changeInfo(map);
        if (bool){
            User newuser = userMapper.getUserById(user.getId());
            modelAndView.addObject("msg","个人信息修改成功");
            request.getSession().setAttribute("user",newuser);
            return modelAndView;
        }else {
            modelAndView.addObject("msg","个人信息修改失败");
            return modelAndView;
        }
    }

    // 前往 homepage
    @RequestMapping("/toMyHomepage")
    public ModelAndView tohomepage(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        String id = user.getId();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("homepage");
        modelAndView.addObject("homeuser",user);
        return modelAndView;
    }
    // 前往别人的 homepage
    @RequestMapping("/toHomepage")
    public ModelAndView toyourhomepage(HttpServletRequest request,@RequestParam String userId){
        User user = (User) userMapper.getUserById(userId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("homepage");
        modelAndView.addObject("homeuser",user);
        return modelAndView;
    }

    // 修改个人简历
    @PostMapping("/addResume")
    public ModelAndView addorchangeresume(@RequestParam Map map,HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("result");
        User user = (User) request.getSession().getAttribute("user");
        String id = user.getId();
        String resume = (String) map.get("my-editormd-html-code");
        boolean bool = userMapper.addOrChangeRuseme(resume,id);
        if (bool){
            User newUser = userMapper.getUserById(id);
            request.getSession().setAttribute("user",newUser);
            modelAndView.addObject("msg","简历修改成功，请前往主页查看");
            return modelAndView;
        }else {
            modelAndView.addObject("msg","简历修改失败");
            return  modelAndView;
        }
    }

}