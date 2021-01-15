package com.itheima.health.service;

import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.OrderSetting;

import java.util.List;

public interface OrderSettiongService {

    /**
     * 批量导入预约设置
     * @param orderSettingList
     * @return
     */
    void addBatch(List<OrderSetting> orderSettingList) throws MyException;
}
