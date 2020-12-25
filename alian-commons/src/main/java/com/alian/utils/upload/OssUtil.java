package com.alian.utils.upload;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public class OssUtil {

    // Endpoint以杭州为例，其它Region请按实际情况填写。
    public static String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
    public static String accessKeyId = "LTAI4G6fEmpxXMG2H1kwPHCD";
    public static String accessKeySecret = "e2j6Frv03w5c7166pDy4l6V3xrXck2";
    public static String fileUrl = "https://weibo-zzl.oss-cn-beijing.aliyuncs.com";
    public static String bucketName = "weibo-zzl";


    public static String uploadfile(String mkdir,MultipartFile file) throws IOException {

        //获取图片的真实名称
        String realName = file.getOriginalFilename();
        //截取后缀名
        String newName = realName.substring(realName.lastIndexOf("."));
        //拼接一个新的文件名称
        String newFileName = UUID.randomUUID().toString()+newName;

        // <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        String path = mkdir+"/"+newFileName;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
        ossClient.putObject(bucketName, path, file.getInputStream());

        // 关闭OSSClient。
        ossClient.shutdown();
        return fileUrl+"/"+path;
    }



}
