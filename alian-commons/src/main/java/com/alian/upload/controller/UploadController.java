package com.alian.upload.controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.alian.utils.result.CommonsReturn;
import com.alian.utils.upload.OssUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/upload")
@CrossOrigin
public class UploadController {

    @PostMapping("/uploadPhoto")
    public CommonsReturn uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date());
        String fileUrl = OssUtil.uploadfile("image"+"/"+format,file);
        return CommonsReturn.success(fileUrl);
    }

}
