package com.itheima.health.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 添加检查组
     * @param
     * @return
     */
    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //添加检查组
        checkGroupDao.add(checkGroup);
        //获取检查组id
        Integer groupid = checkGroup.getId();
        //遍历选中的检查项的id
        if (null != checkitemIds){
            for (Integer checkitemId : checkitemIds) {
                //添加检查项和检查组的关联
                checkGroupDao.addCheckGroupCheckItem(groupid, checkitemId);
            }
        }
        //添加事务控制
    }

    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        //对queryPageBean.getPageSize()进行限制
        int pageSize = (queryPageBean.getPageSize() > 50) ? 50 : queryPageBean.getPageSize();
        queryPageBean.setPageSize(pageSize);

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        //判断查询条件
        if (StringUtils.isNotEmpty(queryPageBean.getQueryString())) {
            //有查询条件,模糊查询
            queryPageBean.setQueryString("%"+ queryPageBean.getQueryString() +"%");
        }
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryPageBean.getQueryString());
        PageResult<CheckGroup> pageResult = new PageResult<CheckGroup>(page.getTotal(), page.getResult());

        return pageResult;
    }
}
