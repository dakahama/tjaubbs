package com.tjau.bbs.tjaubbs.controller;

import com.tjau.bbs.tjaubbs.domain.Process;
import com.tjau.bbs.tjaubbs.mapper.ProcessMapper;
import com.tjau.bbs.tjaubbs.utils.AliyunOSSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
public class UploadProcessController {


    private static final Logger logger = LoggerFactory.getLogger(UploadProcessController.class);

    @Autowired
    ProcessMapper processMapper;

    @Autowired
    private AliyunOSSUtil aliyunOSSUtil;

    @ResponseBody
    @GetMapping("/down")
    public String downloadfile(HttpServletResponse response) throws IOException {
        String fileUrl = "tjaubbs/2019-04-12/ea99fa7164a240dd8b9ed7f16d970461-王安成.pdf";
        boolean b = aliyunOSSUtil.downloadFile(response,fileUrl);
        if (b){

            return "success";
        }else {
            return "failed";
        }

    }

    @RequestMapping("/addProcess")
    public ModelAndView uploadPicture(@RequestParam(value = "file") MultipartFile file, @RequestParam Map map){
        ModelAndView modelAndView = new ModelAndView("result");
        Process process = new Process();
        String filename = file.getOriginalFilename();
        String fileUrl = "tjaubbs"+"/"+("file/"+filename);
        try {
            if (!"".equals(filename.trim())) {
                File newFile = new File(filename);
                FileOutputStream os = new FileOutputStream(newFile);
                os.write(file.getBytes());
                os.close();
                file.transferTo(newFile);

                boolean bool = aliyunOSSUtil.uploadFile(newFile,fileUrl);
                if (bool){
                    //process id
                    int id = (int) System.currentTimeMillis();
                    process.setId(String.valueOf(id));
                    process.setContractId((String) map.get("contractId"));
                    process.setTitle((String) map.get("title"));
                    process.setDescribe((String) map.get("detail"));
                    String filepath = "https://19981015.oss-cn-qingdao.aliyuncs.com/"+fileUrl;
                    process.setFile(filepath);
                    process.setPublicDate(new Date());
                    processMapper.addProcess(process);
                    modelAndView.addObject("msg","上传成功");
                    newFile.delete();
                    return modelAndView;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelAndView.addObject("msg","上传失败");
        return modelAndView;
    }
}
