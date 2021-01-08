package com.itheima.health.constant;

import java.sql.DriverManager;

public class JDBCTest {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println(DriverManager.getConnection("jdbc:mysql://localhost:3306/health?characterEncoding=utf8", "root", "root"));
    }
}
