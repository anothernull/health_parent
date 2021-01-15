package com.itheima.health.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @Override
    @Transactional
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //添加套餐
        setmealDao.add(setmeal);
        //获取套餐的id
        Integer setmealId = setmeal.getId();

        if (null != checkgroupIds){
            for (Integer checkgroupId : checkgroupIds) {
                //添加套餐与检查组的关系
                setmealDao.addSetmealCheckGroup(setmealId, checkgroupId);
            }
        }
        //事务控制
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        //对queryPageBean.getPageSize()进行限制
        int pageSize = (queryPageBean.getPageSize() > 50) ? 50 : queryPageBean.getPageSize();
        queryPageBean.setPageSize(pageSize);

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        //判断查询条件
        if (StringUtils.isNotEmpty(queryPageBean.getQueryString())) {
            //有查询条件,模糊查询
            queryPageBean.setQueryString("%"+ queryPageBean.getQueryString() +"%");
        }
        Page<Setmeal> page = setmealDao.findByCondition(queryPageBean.getQueryString());
        PageResult<Setmeal> pageResult = new PageResult<Setmeal>(page.getTotal(), page.getResult());

        return pageResult;
    }

    /**
     * 通过id查询
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    /**
     * 查询选中的检查组id集合
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckGroupIdsBySetmealId(int id) {
        return setmealDao.findCheckGroupIdsBySetmealId(id);
    }

    /**
     * 编辑套餐
     * @param setmeal
     * @param checkGroupIds
     * @return
     */
    @Override
    @Transactional
    public void update(Setmeal setmeal, Integer[] checkGroupIds) {
        //更新套餐
        setmealDao.update(setmeal);
        //删除旧关系
        setmealDao.deleteSetmealCheckGroup(setmeal.getId());
        //遍历添加新关系
        if (null != checkGroupIds) {
            for (Integer checkGroupId : checkGroupIds) {
                setmealDao.addSetmealCheckGroup(setmeal.getId(), checkGroupId);
            }
            //事务控制
        }
    }

    /**
     * 删除套餐
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(Integer id) {
        // 是否存在订单，如果存在则不能删除
        int count = setmealDao.findOrderCountBySetmealId(id);
        if(count > 0){
            // 已经有订单使用了这个套餐，不能删除
            throw new MyException("已经有订单使用了这个套餐，不能删除！");
        }
        // 先删除套餐与检查组的关系
        setmealDao.deleteSetmealCheckGroup(id);
        // 再删除套餐
        setmealDao.deleteById(id);
    }

    /**
     * 查询所以图片
     * @return
     */
    @Override
    public List<String> fingImgs() {
        return setmealDao.fingImgs();
    }
}
