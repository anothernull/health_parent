package com.itheima.health.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.OrderSettiongDao;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettiongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettiongService.class)
public class OrderSettiongServiceImpl implements OrderSettiongService {

    @Autowired
    private OrderSettiongDao orderSettiongDao;

    /**
     * 批量导入预约设置
     *
     * @param orderSettingList
     * @return
     */
    @Override
    @Transactional
    public void addBatch(List<OrderSetting> orderSettingList) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //判断List<Ordersetting>不为空
        if (!CollectionUtils.isEmpty(orderSettingList)) {
            //遍历导入的预约设置信息List<Ordersetting>
            for (OrderSetting os : orderSettingList) {
                //通过预约的日期来查询预约设置表，看这个日期的设置信息有没有
                OrderSetting osInDB = orderSettiongDao.findByOrderDate(os.getOrderDate());
                //没有预约设置(表中没有这个日期的记录)
                if (osInDB == null) {
                    //调用dao插入数据
                    orderSettiongDao.add(os);


                    //有预约设置(表中有这个日期的记录)
                } else {
                    //已预约人数
                    int reservations = osInDB.getReservations();
                    //要更新的最大预约值
                    int number = os.getNumber();

                    //判断已预约人数是否大于要更新的最大预约数
                    if (reservations > number){
                        //大于则要报错，接口方法 异常声明
                        throw new MyException(sdf.format(os.getOrderDate()) + ":最大预约数不能小于已已预约数");
                    }else {
                        //小于，则可以更新最大预约数
                        orderSettiongDao.updateNumber(os);
                    }
                }
            }
        }
        //事务控制
    }

    /**
     * 通过月份查询预约设置信息
     * @param month
     * @return
     */
    @Override
    public List<Map<String, Integer>> getOrderSettingByMonth(String month) {
        month += "%";
        return orderSettiongDao.getOrderSettingByMonth(month);
    }
}
