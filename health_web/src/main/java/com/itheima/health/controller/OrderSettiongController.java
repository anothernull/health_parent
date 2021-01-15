package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.Constant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettiongService;
import com.itheima.health.utils.POIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettiongController {

    @Reference(interfaceClass = OrderSettiongService.class)
    private OrderSettiongService orderSettiongService;

    private static final Logger log = LoggerFactory.getLogger(OrderSettiongController.class);

    /**
     * 批量导入预约设置
     * @param excelFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile excelFile){
        //解析excel文件，调用POIUitls解析文件，得到List<String[]> ，
        //每一个数组代表一行记录
        try {
            List<String[]> excelData = POIUtils.readExcel(excelFile);
            log.debug("导入预约设置功能读取到了{}条", excelData.size());

            // 转成List<Ordersetting>，string[] 长度为2, 0:日期, 1:数量
            SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            List<OrderSetting> orderSettingList = excelData.stream().map(arr -> {
                OrderSetting os = new OrderSetting();
                try {
                    os.setOrderDate(sdf.parse(arr[0]));
                } catch (ParseException e) {
                }
                os.setNumber(Integer.valueOf(arr[1]));
                return os;
            }).collect(Collectors.toList());

            // 调用service 方法做导入，
            orderSettiongService.addBatch(orderSettingList);

            // 返回给页面
            return new Result(true, Constant.IMPORT_ORDERSETTING_SUCCESS);

        } catch (IOException e) {
            log.error("导入预约设置失败", e);
            return new Result(false, Constant.IMPORT_ORDERSETTING_FAIL);
        }

    }
}
