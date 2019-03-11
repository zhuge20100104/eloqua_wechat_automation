package com.oracle.auto.web.utility;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class SpringUtils {

    private static ClassPathXmlApplicationContext classPathXmlApplicationContext;

    public static ApplicationContext getApplicationContext() {
        if (classPathXmlApplicationContext == null)
            classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new ClassPathResource(PropertyConfiger.instance().getEnvData("spring.context.xml", "config.xml")).getPath());
        return classPathXmlApplicationContext;
    }

}
