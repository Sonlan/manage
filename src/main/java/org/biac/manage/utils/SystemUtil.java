package org.biac.manage.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Song on 2016/7/12.
 */
public class SystemUtil {
    private static final Pattern PATTERN = Pattern.compile("\\$\\{([^\\}]+)\\}");
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

    public static String getProperty(String key) {
        String value = prop.getProperty(key);
        Matcher matcher = PATTERN.matcher(value);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String matcherKey = matcher.group(1);
            String matchervalue = prop.getProperty(matcherKey);
            if (matchervalue != null) {
                matcher.appendReplacement(buffer, matchervalue);
            }
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }


}
