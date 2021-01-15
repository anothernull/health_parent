package com.itheima.health.jobs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class CleanImgJob {

    private static final Logger log = LoggerFactory.getLogger(CleanImgJob.class);

    @Reference(interfaceClass = SetmealService.class)
    private SetmealService setmealService;

    /**
     * 调用任务类，清理七牛上的垃圾图片
     */
    @Scheduled(initialDelay = 3000, fixedDelay = 1800000)
    public void cleanImg(){
        log.info("开始执行清理垃圾图片");
        //调用QiNiuUtils.查询所有的图片
        List<String> imgIn7Niu = QiNiuUtils.listFile();
        log.debug("7牛上一共有{}张图片", imgIn7Niu.size());
        //调用setmealService查询数据库的所有图片
        List<String> imgInDb = setmealService.fingImgs();
        log.debug("数据库中一共有{}张图片", imgInDb == null ? 0: imgInDb.size());
        //把七牛上的减去数据库的，剩下的就是要删除的图片
        imgIn7Niu.removeAll(imgInDb);
        if (imgIn7Niu.size() > 0) {
            log.debug("要清理的图片有{}张", imgIn7Niu.size());
            //调用七牛工具删除垃圾图片
            QiNiuUtils.removeFiles(imgIn7Niu.toArray(new String[]{}));
            log.info("清理垃圾图片完成");
        } else {
            log.debug("没有要清理的图片有{}张");
        }
    }
}
