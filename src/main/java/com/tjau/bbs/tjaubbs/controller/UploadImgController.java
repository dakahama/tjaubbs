package com.tjau.bbs.tjaubbs.controller;

import com.tjau.bbs.tjaubbs.utils.AliyunOSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class UploadImgController {


    @Autowired
    AliyunOSSUtil aliyunOSSUtil;

    @ResponseBody
    @RequestMapping("/uploadtaskimgdemo")
    public Map<String,Object> uploadtaskimgtest(@RequestParam(value="editormd-image-file",required = false)MultipartFile file,
                                            HttpServletRequest request){

        Map<String,Object> resultMap = new HashMap<>();

        String realPath = "F://tjaubbs//";
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
/*        File targetFile = new File(realPath, fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }*/
        //保存
        try {
            /*            file.transferTo(targetFile);*/
            byte[] bytes = file.getBytes();
            Path path = Paths.get(realPath + "demo2.jpg");
            Files.write(path, bytes);
            resultMap.put("success", 1);
            resultMap.put("message", "上传成功！");
            resultMap.put("url",realPath+fileName);
        } catch (Exception e) {
            resultMap.put("success", 0);
            resultMap.put("message", "上传失败！");
            e.printStackTrace();
        }
        System.out.println(resultMap.get("success"));
        return resultMap;

    }

    @ResponseBody
    @RequestMapping("/uploadImg")
    public Map<String,Object> uploadtaskimg(@RequestParam(value="editormd-image-file",required = false) MultipartFile file,
                                            HttpServletRequest request){

        Map<String,Object> resultMap = new HashMap<>();

        //得到上传文件的后缀
        String originalFilename = file.getOriginalFilename();
        //System.out.println(originalFilename);
        int i = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(i);


        try {
            if (!"".equals(originalFilename.trim())) {

                String uuld = UUID.randomUUID().toString().replace("-","");
                String filename = uuld+suffix;

                String fileUrl = "tjaubbs"+"/"+("task/img/"+filename);

                File newFile = new File(filename);
                FileOutputStream os = new FileOutputStream(newFile);
                os.write(file.getBytes());
                os.close();
                file.transferTo(newFile);

                boolean b = aliyunOSSUtil.uploadFile(newFile,fileUrl);


                if (b){
                    resultMap.put("success",1);
                    resultMap.put("message","upload img success");

                    String url = "https://19981015.oss-cn-qingdao.aliyuncs.com/tjaubbs/task/img/"+filename;
                    resultMap.put("url",url);

                    boolean bool = newFile.delete();
                }else {
                    resultMap.put("success",0);
                    resultMap.put("message","upload img fail");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

}
