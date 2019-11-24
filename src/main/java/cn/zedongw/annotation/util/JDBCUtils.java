package cn.zedongw.annotation.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;

/**
 * @Author ZeDongW
 * @ClassName JDBCUtils
 * @Description C3P0数据库连接工具
 * @Version 3.0
 * @date ：Created in 2019/7/11 7:02
 * @modified By：
 */

public class JDBCUtils {
    //DBCP连接池
    private static ComboPooledDataSource comboPooledDataSource;
    //静态代码块初始化数据库连接池
    static {
        try {
            comboPooledDataSource = new ComboPooledDataSource();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Author: ZeDongW
     * @Description: 返回DBUtil对象
     * @Date: 2019/7/11 7:15
     * @Param: []
     * @return: org.apache.commons.dbutils.QueryRunner
     */ 
    public static QueryRunner getQueryRunner(){
        return new QueryRunner(comboPooledDataSource);
    }
}
