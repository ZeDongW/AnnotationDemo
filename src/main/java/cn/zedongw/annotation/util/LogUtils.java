package cn.zedongw.annotation.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.*;

/**
 * @ClassName LogUtils
 * @Description: log4j2日志工具类
 * @Author ZeDongW
 * @Date 2019/11/24/0024 23:58
 * @Version 1.0
 * @Modified By:
 * @Modified Time:
 **/
public class LogUtils {

    public static Logger getLogger(){
        try {
            InputStream resourceAsStream = LogUtils.class.getClassLoader().getResourceAsStream("log4j2.xml");
            BufferedInputStream in = new BufferedInputStream(resourceAsStream);
            final ConfigurationSource source = new ConfigurationSource(in);
            Configurator.initialize(null, source);

            return LogManager.getLogger();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
