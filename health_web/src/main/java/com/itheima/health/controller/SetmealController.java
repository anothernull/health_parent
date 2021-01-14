package com.itheima.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.Constant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

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

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        setmealService.add(setmeal, checkgroupIds);
        return new Result(true, Constant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Setmeal> pageResult = setmealService.findPage(queryPageBean);
        return new Result(true, Constant.QUERY_SETMEAL_SUCCESS, pageResult);
    }

    /**
     * 通过id查询
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(Integer id){
        Setmeal setmeal = setmealService.findById(id);
        //构建前端需要的数据，还要域名
        Map<String, Object> map = new HashMap<>(2);
        map.put("setmeal", setmeal);
        map.put("domain", QiNiuUtils.DOMAIN);
        return new Result(true, Constant.QUERY_SETMEALLIST_SUCCESS, map);
    }

    /**
     * 查询选中的检查组id集合
     * @param id
     * @return
     */
    @GetMapping("/findCheckGroupIdsBySetmealId")
    public Result findCheckGroupIdsBySetmealId(int id){
        List<Integer> checkGroupIds = setmealService.findCheckGroupIdsBySetmealId(id);
        return new Result(true, Constant.QUERY_SETMEAL_SUCCESS, checkGroupIds);
    }

    /**
     * 编辑套餐
     * @param setmeal
     * @param checkGroupIds
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal, Integer[] checkGroupIds){
        setmealService.update(setmeal, checkGroupIds);
        return new Result(true, "编辑套餐成功");
    }

    /**
     * 删除套餐
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public Result deleteById(Integer id){
        setmealService.deleteById(id);
        return new Result(true,"删除套餐成功");
    }
}
