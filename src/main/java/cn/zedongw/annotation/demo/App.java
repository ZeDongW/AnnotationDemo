package cn.zedongw.annotation.demo;

import cn.zedongw.annotation.dao.UsersDao;
import cn.zedongw.annotation.util.LogUtils;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

/**
 * @ClassName App
 * @Description: 演示类
 * @Author ZeDongW
 * @Date 2019/11/24/0024 23:38
 * @Version 1.0
 * @Modified By:
 * @Modified Time:
 **/
public class App {
    @Test
    public void testDao() throws Exception {
        UsersDao adminDao = new UsersDao();
        Logger logger = LogUtils.getLogger();
        logger.info(adminDao.findById(2));
        logger.info(adminDao.findAll());
    }
}
