package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {

    /**
     * 查询所有
     */
    List<CheckItem> findAll();

    /**
     * 新增检查项
     * @return
     */
    void add(CheckItem checkItem);

    /**
     * 检查项的分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    /**
     * 通过id查询检查项
     * @param id
     * @return
     */
    CheckItem findById(int id);

    /**
     * 修改检查项
     * @return
     */
    void update(CheckItem checkItem);
}
