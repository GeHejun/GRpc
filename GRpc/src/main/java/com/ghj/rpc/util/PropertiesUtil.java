package com.ghj.rpc.util;

import com.ghj.rpc.core.Registry;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    /**
     * 加载
     * @return
     * @throws IOException
     */
    public static String loadProperties(String path, String key) throws IOException {
        InputStream resourceAsStream = Registry.class.getResourceAsStream(path);
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        String packageName = (String) properties.get(key);
        return packageName;
    }
}
