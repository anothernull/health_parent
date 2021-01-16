package com.itheima.health.service;

import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettiongService {

    /**
     * 批量导入预约设置
     * @param orderSettingList
     * @return
     */
    void addBatch(List<OrderSetting> orderSettingList) throws MyException;

    /**
     * 通过月份查询预约设置信息
     * @param month
     * @return
     */
    List<Map<String, Integer>> getOrderSettingByMonth(String month);

    /**
     * 通过日期修改最大预约数
     * @param orderSetting
     * @return
     */
    void editNumberByDate(OrderSetting orderSetting) throws MyException;
}
