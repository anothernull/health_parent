package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettiongDao {

    /**
     * 通过预约的日期来查询预约设置表，看这个日期的设置信息有没有
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderDate(Date orderDate);

    /**
     * 插入数据
     * @param os
     */
    void add(OrderSetting os);

    /**
     * 更新最大预约数
     * @param os
     */
    void updateNumber(OrderSetting os);

    /**
     * 通过月份查询预约设置信息
     * @param month
     * @return
     */
    List<Map<String, Integer>> getOrderSettingByMonth(String month);
}
