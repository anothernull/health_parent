package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.Constant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    /**
     * 订阅检查项服务
     */
    @Reference
    private CheckItemService checkItemService;

    /**
     * 新增检查项
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        checkItemService.add(checkItem);
        return new Result(true, Constant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 修改检查项
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem){
        checkItemService.update(checkItem);
        return new Result(true, Constant.EDIT_CHECKITEM_SUCCESS);
    }

    /**
     * 删除检查项
     * @return
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id){
            checkItemService.deleteById(id);
            return new Result(true, Constant.DELETE_CHECKITEM_SUCCESS);
    }

    /**
     * 检查项的分页查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
        return new Result(true, Constant.QUERY_CHECKITEM_SUCCESS, pageResult);
    }

    /**
     * 通过id查询检查项
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){
        CheckItem checkItem = checkItemService.findById(id);
        return new Result(true, Constant.QUERY_CHECKITEM_SUCCESS, checkItem);
    }

    /**
     * 查询所有
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckItem> checkItemList = checkItemService.findAll();
        return new Result(true, Constant.QUERY_CHECKITEM_SUCCESS, checkItemList);
    }
}
