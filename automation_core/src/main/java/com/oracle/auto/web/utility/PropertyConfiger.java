package com.oracle.auto.web.utility;

import org.apache.log4j.Logger;
import org.jbehave.core.io.LoadFromClasspath;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

public class PropertyConfiger {
    private static Logger log = Logger.getLogger(PropertyConfiger.class);
    private static PropertyConfiger instance = new PropertyConfiger();

    private PropertyConfiger() {
    }

    public static PropertyConfiger instance() {
        return instance;
    }

    private LoadFromClasspath classLoader = null;

    public PropertyConfiger setClassLoader(Class<?> embeddableClass) {
        classLoader = new LoadFromClasspath(embeddableClass);
        return this;
    }

    public PropertyConfiger loadProperties(List<String> filePaths) {
        if (classLoader == null) {
            classLoader = new LoadFromClasspath(this.getClass());
            log.warn("**warning** class loader of Property Configer is empty, use default one");
        }

        for (String filePath : filePaths) {
            log.debug("processing property file:" + filePath);
            process(filePath);
        }

        return this;
    }

    private void process(String filePath) {
        Properties properties = new Properties();
        try {
            String content = classLoader.loadResourceAsText(filePath);
            InputStream inputStream = new ByteArrayInputStream(content.getBytes());
            UnicodeReader in = new UnicodeReader(inputStream, "UTF8");
            properties.load(in);
        } catch (Exception e) {
            log.error("Failed to load properties from path: " + filePath);
            System.exit(1);
        }

        List<String> keyList = new ArrayList<>();
        for (Object key : properties.keySet()) {
            keyList.add(String.valueOf(key));
        }
        Collections.sort(keyList);

        for (String key : keyList) {
            String updatedValue = System.getProperty(key);
            if (updatedValue != null) {
                String defaultValue = updatedValue;
                log.warn("use updated system property: " + key + " = " + defaultValue);
                System.setProperty(key, defaultValue);
            } else {
                String defaultValue = properties.getProperty(key);
                log.debug("Using default system property: " + key + " = " + defaultValue);
                System.setProperty(key, defaultValue);
            }
        }
    }

    private ThreadLocal<Map<String, String>> threadContext = new ThreadLocal<Map<String, String>>();

    public void setThreadContext(Map<String, String> context) {
        threadContext.set(context);
    }

    ///utility method
    public String getEnvData(String item, String def) {
        // get context firstly (if any)
        Map<String, String> context = threadContext.get();
        if (context != null) {
            String data = context.get(item);
            if (data != null && !data.isEmpty()) {
                log.debug("get thread context property [" + item + "] : " + data);
                return data;
            }
        }


        // get env first, then get system property
        String data = System.getenv(item);
        if (data == null || data == "") {
            data = System.getProperty(item);
            if (data == null || data == "") {
                log.debug("get empty system env or property [" + item + "], use default: " + def);
                return def;
            }
        }

        log.debug("get system env or property [" + item + "] = " + data);
        return data;
    }

    public String setEnvData(String item, String value) {
        String data = System.setProperty(item, value);
        log.debug("set system env or property [" + item + "] = " + data);
        return data;
    }


    public boolean getEnvData(String item, boolean def) {
        String result = getEnvData(item, "");
        if (result == "")
            return def;
        try {
            return Boolean.parseBoolean(result);
        } catch (Exception ex) {
            log.error("fail to parse system value [" + item + "]=[" + result + "] to boolean. use default def = " + def, ex);
            return def;
        }
    }

    public int getEnvData(String item, int def) {
        String result = getEnvData(item, "");
        if (result == "")
            return def;
        try {
            return Integer.parseInt(result);
        } catch (Exception ex) {
            log.error("fail to parse system value [" + item + "]=[" + result + "] to int. use default def = " + def, ex);
            return def;
        }
    }

}
