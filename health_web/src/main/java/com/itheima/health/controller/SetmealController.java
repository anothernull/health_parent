package com.itheima.health.controller;


import com.itheima.health.constant.Constant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    private static Logger log = LoggerFactory.getLogger(SetmealController.class);

    /**
     * 上传图片
     * @param imgFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        //1.获取源文件名
        String originalFilename = imgFile.getOriginalFilename();
        //2.截取后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //3.生成唯一id
        String onlyId = UUID.randomUUID().toString();
        //4.拼接唯一文件名
        String fileName = onlyId + suffix;
        //5.调用工具类上传
        try {
            QiNiuUtils.uploadViaByte(imgFile.getBytes(), fileName);
        } catch (IOException e) {
            log.error("上传文件失败", e);
            return new Result(false, Constant.PIC_UPLOAD_FAIL);
        }
        //6.构建返回的数据
        Map<String, String> map = new HashMap<>();
        map.put("imgName", fileName);
        map.put("domain", QiNiuUtils.DOMAIN);
        //7.封装入result，响应给页面
        return new Result(true, Constant.PIC_UPLOAD_SUCCESS, map);

    }
}
