package org.biac.manage.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by Song on 2016/7/12.
 */
public class SystemUtil {
    private static Logger logger = LoggerFactory.getLogger(SystemUtil.class);
    private static Properties prop;
    static {
        try{
            prop =  new Properties();
            prop.load(new InputStreamReader(SystemUtil.class.getClassLoader().getResourceAsStream("setting/biac.properties"), "UTF-8"));
        }catch (Exception e){
            logger.error("读取系统配置文件失败:"+e.toString());
        }
    }
    public static String getDomain(){
        return prop.getProperty("domain","");
    }
}
