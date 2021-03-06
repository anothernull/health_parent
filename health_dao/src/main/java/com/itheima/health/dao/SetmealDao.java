package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetmealDao {
    /**
     * 添加套餐
     * @param setmeal
     * @return
     */
    void add(Setmeal setmeal);

    /**
     * 添加套餐与检查组的关系
     * @param setmealId
     * @param checkgroupId
     */
    void addSetmealCheckGroup(@Param("setmealId") Integer setmealId,@Param("checkgroupId") Integer checkgroupId);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<Setmeal> findByCondition(String queryString);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    Setmeal findById(Integer id);

    /**
     * 查询选中的检查组id集合
     * @param id
     * @return
     */
    List<Integer> findCheckGroupIdsBySetmealId(int id);

    /**
     * 更新套餐
     * @param setmeal
     */
    void update(Setmeal setmeal);

    /**
     * 删除旧关系
     * @param id
     */
    void deleteSetmealCheckGroup(Integer id);

    /**
     * 通过套餐的id查询使用了这个套餐的订单个数
     * @param id
     * @return
     */
    int findOrderCountBySetmealId(Integer id);

    /**
     * 通过id删除套餐信息
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 查询所以图片
     * @return
     */
    List<String> fingImgs();

    /**
     * 查询所有的套餐
     * @return
     */
    List<Setmeal> findAll();

    /**
     * 通过id查询套餐详情
     * @param id
     * @return
     */
    Setmeal findDetailById(int id);
}
