package com.oracle.auto.web.pages.object;

import com.oracle.auto.web.utility.PropertyConfiger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class ApplicationContextLocal {
    private static final ThreadLocal<ApplicationContext> applicationContext = new
            ThreadLocal<ApplicationContext>() {
                @Override
                protected ApplicationContext initialValue() {
                    return new ClassPathXmlApplicationContext(new ClassPathResource(PropertyConfiger.instance().
                            getEnvData("spring.context.xml", "config.xml"))
                            .getPath());

                }
            };
    public static ApplicationContext get(){
        return applicationContext.get();
    }

}


