package com.tjau.bbs.tjaubbs.controller;

import com.tjau.bbs.tjaubbs.domain.User;
import com.tjau.bbs.tjaubbs.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

@Controller
public class LoginAndRegisterController {

    @Autowired
    UserMapper userMapper;

    //转到登入界面
    @RequestMapping("/login")
    public String toLoginPage(){
        return "login/login";
    }

    //进行登入验证
    @PostMapping("/login")
    public ModelAndView login(@RequestParam Map<String,Object> paramMap, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        String id = (String) paramMap.get("id");
        String password = (String) paramMap.get("password");

        User user = userMapper.getUserById(id);
        HttpSession session = request.getSession();
        if (user==null){
            modelAndView.setViewName("login/login");
            modelAndView.addObject("msg","账号不存在");
            return modelAndView;
        }
        if (user.getPassword().equals(password)){
            session.setAttribute("user",user);
            modelAndView.setViewName("index");
            return modelAndView;
        }else {
            modelAndView.addObject("msg","密码错误");
            modelAndView.setViewName("login/login");
            return modelAndView;
        }
    }

    //转到注册界面
    @RequestMapping("/register")
    public String toRegister(){
        return "login/register";
    }

    @PostMapping("/register")
    public ModelAndView register(@RequestParam Map<String,Object> paramMap, HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        String id = (String) paramMap.get("stuid");
        String realname = (String) paramMap.get("realname");
        String username = (String) paramMap.get("username");
        String password = (String) paramMap.get("password");
        String repassword = (String) paramMap.get("repassword");
        String email = (String) paramMap.get("email");
        String college = (String) paramMap.get("college");
        String major = (String) paramMap.get("major");


        int gender = 0;
        String inputgender = (String) paramMap.get("gender");
        if(inputgender!=null){
            if (inputgender.equals("woman")) gender = 1;
        }

        String sign = (String) paramMap.get("sign");

        Date date = new Date();
        HttpSession session = request.getSession();

        User user = userMapper.getUserById(id);
        if(user!=null){
            modelAndView.addObject("msg","注册的账号已存在");
            modelAndView.setViewName("result");
            return modelAndView;
        }else {
            if (password.equals(repassword)){
                User student = new User(id,realname,email,username,sign,college,major,date,password,0);
                if (!sign.equals("")){
                    student.setSign(sign);
                }else {
                    student.setSign("未设置个性签名");
                }

                boolean result = userMapper.register(student);

                if (result){
                    modelAndView.addObject("msg","注册成功，请前往登入");
                    modelAndView.setViewName("result");
                    return modelAndView;
                }else {
                    modelAndView.addObject("msg","注册失败");
                    modelAndView.setViewName("result");
                    return modelAndView;
                }
            }else {
                modelAndView.setViewName("result");
                modelAndView.addObject("msg","两次密码不一样");
                return modelAndView;
            }
        }
    }
}
