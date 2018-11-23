package com.github.sjroom.common.jdbc.generatecode;


import java.sql.*;


/**
 * Created by zhouwei on 16/11/5.
 */
public class DBHelper {


    private static Connection conn = null;
    private static PreparedStatement pstmt = null;
    private static ResultSet rs = null;

    //四个方法
    public static Connection getConnection(String jdbcDriver, String jdbcUrl, String userName, String password) {
        try {
            if (conn != null) {
                return conn;
            }
            //1: 加载连接驱动，Java反射原理
            Class.forName(jdbcDriver);
            //2:创建Connection接口对象，用于获取MySQL数据库的连接对象。三个参数：url连接字符串    账号  密码
            conn = DriverManager.getConnection(jdbcUrl, userName, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConn() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static int execOther(PreparedStatement pstmt) {
        try {
            //1、使用Statement对象发送SQL语句
            int affectedRows = pstmt.executeUpdate();
            //2、返回结果
            return affectedRows;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


    public static ResultSet execQuery(String sql) {
        try {
            // 调用SQL
            pstmt = conn.prepareStatement(sql);
            //1、使用Statement对象发送SQL语句
            rs = pstmt.executeQuery();
            //2、返回结果
            return rs;
        } catch (SQLException e) {
            closeConn();
            e.printStackTrace();
            return null;
        }
    }

}
