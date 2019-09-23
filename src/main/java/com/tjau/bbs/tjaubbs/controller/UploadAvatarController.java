package com.tjau.bbs.tjaubbs.controller;


import com.tjau.bbs.tjaubbs.domain.User;
import com.tjau.bbs.tjaubbs.mapper.UserMapper;
import com.tjau.bbs.tjaubbs.utils.AliyunOSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class UploadAvatarController {
    @Autowired
    AliyunOSSUtil aliyunOSSUtil;
    @Autowired
    UserMapper userMapper;

    @PostMapping("/uploadAvatar")
    @ResponseBody
    public String uploadavatar(@RequestParam("file") String file, HttpServletRequest request) throws IOException {

        file = file.substring(23);
        Base64.Decoder decoder = Base64.getDecoder();
        //System.out.println(file);

        byte[] imgByte = decoder.decode(file);

        String uuid = UUID.randomUUID().toString().replace("-","");
        String filename = uuid+".jpg";
        FileOutputStream os = new FileOutputStream(filename);
        os.write(imgByte);
        os.close();

        File newfile = new File(filename);

        User user = (User) request.getSession().getAttribute("user");
        String id= user.getId();
        String fileUrl = "tjaubbs"+"/"+("avatar/"+id+"/"+filename);

        boolean bool = aliyunOSSUtil.uploadFile(newfile,fileUrl);

        if (bool){
            String str1 = "https://19981015.oss-cn-qingdao.aliyuncs.com/";
            String avatarurl = str1+fileUrl;
            System.out.println(avatarurl);
            userMapper.changeAvatar(avatarurl,id);
            newfile.delete();
            return avatarurl;

        }else {
            return "上传失败";
        }

    }
}
