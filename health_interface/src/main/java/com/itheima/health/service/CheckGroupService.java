package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {

    /**
     * 添加检查组
     * @param
     * @return
     */
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 分页查询检查组
     * @param
     * @return
     */
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    /**
     * 通过id查询检查组
     * @param id
     * @return
     */
    CheckGroup findById(int id);

    /**
     * 查询被勾选的检查项
     * @param id
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    /**
     * 修改检查组
     * @return
     */
    void update(CheckGroup checkGroup, Integer[] checkitemIds);
}
