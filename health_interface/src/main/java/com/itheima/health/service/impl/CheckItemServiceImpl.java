package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.alibaba.dubbo.qos.server.DubboLogo.dubbo;

//使用Alibaba的包发布服务 interfaceClass指定服务的接口类
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {
    
    @Autowired
    private CheckItemDao checkItemDao;
    
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
