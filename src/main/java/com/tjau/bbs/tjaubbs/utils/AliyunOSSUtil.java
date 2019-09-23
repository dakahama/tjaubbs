package com.tjau.bbs.tjaubbs.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.tjau.bbs.tjaubbs.config.AliyunOSSConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
public class AliyunOSSUtil {

    @Autowired
    private AliyunOSSConfig aliyunOSSConfig;

    private static final Logger logger = LoggerFactory.getLogger(AliyunOSSUtil.class);

    public boolean uploadFile(File file, String fileUrl) {

        logger.info("----oss file upload begin----" + file.getName());

        String endpoint = aliyunOSSConfig.getEndpoint();
        String accessKeyId = aliyunOSSConfig.getAccessKeyId();
        String accessKeySecret = aliyunOSSConfig.getAccessKeySecret();
        String bucketName = aliyunOSSConfig.getBucketName();
        String fileHost = aliyunOSSConfig.getFilehost();

        if (!file.exists()) {
            return false;
        }

        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        try {
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
                return false;
            }
            // 上传文件

            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, fileUrl, file));
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            if (result != null) {
                logger.info("----oss file upload success");
            }
        } catch (OSSException oe) {
            logger.error(oe.getMessage());
        } catch (ClientException ce) {
            logger.error(ce.getErrorMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return true;
    }



    public String deleteFile(String fileUrl) {

        // fileUrl = tjaubbs/...../...jpg
        logger.info("----开始文件删除----");
        String endpoint = aliyunOSSConfig.getEndpoint();
        String accessKeyId = aliyunOSSConfig.getAccessKeyId();
        String accessKeySecret = aliyunOSSConfig.getAccessKeySecret();
        String bucketName = aliyunOSSConfig.getBucketName();
        String fileHost = aliyunOSSConfig.getFilehost();


        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {

            if (!ossClient.doesBucketExist(bucketName)) {
                logger.info("your bucket doest exist");
                return "your bucket doest exist";
            } else {
                logger.info("begin deleting");
                ossClient.deleteObject(bucketName, fileUrl);
                logger.info("delete successfully");
                return "delete successfylly";

            }

        } catch (Exception ex) {
            logger.info("delete file failed");
            return "delete file failed";
        } finally {

            ossClient.shutdown();
        }

    }


    public boolean downloadFile(HttpServletResponse response,String fileUrl){


        // fileUrl = tjaubbs/...../...jpg
        logger.info("----开始文件下载----");
        String endpoint = aliyunOSSConfig.getEndpoint();
        String accessKeyId = aliyunOSSConfig.getAccessKeyId();
        String accessKeySecret = aliyunOSSConfig.getAccessKeySecret();
        String bucketName = aliyunOSSConfig.getBucketName();
        String fileHost = aliyunOSSConfig.getFilehost();

        //保存路径
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        OSSObject ossObject = ossClient.getObject(bucketName,fileUrl);

        String[] strings = fileUrl.split("/");
        String fileName = strings[strings.length-1];
        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;

        byte[] buffer = new byte[1024];

        try {
            inputStream = new BufferedInputStream(ossObject.getObjectContent());
            outputStream = new BufferedOutputStream(response.getOutputStream());
            OutputStream os = response.getOutputStream();

            response.setHeader("Content-Disposition", "attachment;filename=" +new String(fileName.getBytes("gb2312"),"ISO8859-1"));

            int L = inputStream.read(buffer);
            while (L != -1) {
                os.write(buffer, 0, L);
                L = inputStream.read(buffer);
            }


            System.out.println(fileName);

        }catch (Exception e){
            e.printStackTrace();

            return false;
        }finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ossClient.shutdown();

        }

        return true;
    }



    /*
    public boolean downloadFile(HttpServletResponse response, String fileUrl) throws IOException {

        // filrUrl = "https://19981015.oss-cn-qingdao.aliyuncs.com/tjaubbs/2019-04-12/ea99fa7164a240dd8b9ed7f16d970461-%E7%8E%8B%E5%AE%89%E6%88%90.pdf"


        File file = new File("fileUrl");
        if (file.exists()) {

            response.setContentType("application/x-download");

            String fileName = UUID.randomUUID().toString();
            //设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);

            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                return true;


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    bis.close();
                }

                if (fis != null) {
                    fis.close();
                }


            }
        }
        else {
            System.out.println("wen jian bu cunzai");
        }
        return false;

    }
    */


}







