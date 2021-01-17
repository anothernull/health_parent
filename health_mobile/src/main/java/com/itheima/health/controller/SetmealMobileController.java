package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.Constant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {

    @Reference
    private SetmealService setmealService;

    /**
     * 查询所有的套餐
     * @return
     */
    @GetMapping("/getSetmeal")
    public Result getSetmeal(){
        //调用服务查询所以套餐
        List<Setmeal> list = setmealService.findAll();
        //拼接完整路径
        list.forEach(s->s.setImg(QiNiuUtils.DOMAIN + s.getImg()));
        //放回
        return new Result(true, Constant.QUERY_SETMEAL_SUCCESS, list);
    }
}
