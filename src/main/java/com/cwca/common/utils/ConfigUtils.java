package com.cwca.common.utils;

import com.cwca.common.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Properties;

/**
 * @ Author     ：wongs.
 * @ Date       ：Created in 2018/12/25 - 13:38
 * @ Description：
 */
@Slf4j
public class ConfigUtils {
    private static Properties properties;

    private static String filePath;

    static {
        try {
            filePath = FileUtils.getFilePath("config.properties", Constants.Env.BASE_HOME);
            if (filePath != null) {
                //系统参数配置
                properties = loadProperties("config.properties");
            }
        } catch (Exception e) {
            //ignore
        }
    }


    public static Properties loadProperties(String fileName) {
        return loadProperties(fileName, Constants.Env.BASE_HOME);
    }

    public static Properties loadProperties(String fileName, String filePath) {
        Properties prop = new Properties();
        try {
            File file = FileUtils.getFile(fileName, filePath);
            if (file != null) {
                InputStream input = new FileInputStream(file);
                prop.load(new InputStreamReader(input, "UTF-8"));
            }
        } catch (Exception e) {
            log.error("Loading config.properties fails", e);
        }

        return prop;
    }

    /**
     * 根据Key  得到config文件中key对应的数据
     *
     * @param key
     * @return
     */
    public static String getValue(String key) {
        String value = null;
        try {
            value = new String(properties.getProperty(key).getBytes(), "UTF-8");
        } catch (Exception e) {
            log.error("key:" + key + " 资源参数加载失败！", e);
        }
        return value;

    }

    /**
     * @param key
     * @param value
     */
    public static void setProperties(String key, String value) {
        try {
            FileInputStream input = new FileInputStream(filePath);
            SafeProperties safeProp = new SafeProperties();
            safeProp.load(input);
            input.close();
            if (!"".equals(value) && value != null) {
                // safeProp.addComment("New Comment测试");
                safeProp.put(key, value);
            }
            if (key != null) {
                if (value == null || "".equals(value)) {
                    safeProp.remove(key);
                }
            }
            FileOutputStream output = new FileOutputStream(filePath);
            safeProp.store(output, null);
            output.close();
        } catch (Exception e) {
            log.error("Visit " + filePath + " for updating " + key + " value error", e.getMessage());
        }

    }

    /**
     * 删除
     *
     * @param key
     */
    public static void removeProperties(String key) {
        try {
            FileInputStream input = new FileInputStream(filePath);
            SafeProperties safeProp = new SafeProperties();
            safeProp.load(input);
            input.close();
            if (key != null) {
                safeProp.remove(key);
            }
            FileOutputStream output = new FileOutputStream(filePath);
            safeProp.store(output, null);
            output.close();
        } catch (Exception e) {
            log.error("Visit " + filePath + " for updating " + key + " value error", e.getMessage());
        }

    }

}
