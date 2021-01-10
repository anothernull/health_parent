package com.itheima.health.dao;

import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void addCheckGroupCheckItem(@Param("groupid") Integer groupid, @Param("checkitemId") Integer checkitemId);
}