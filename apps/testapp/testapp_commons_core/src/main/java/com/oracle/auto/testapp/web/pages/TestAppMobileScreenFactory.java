package com.oracle.auto.testapp.web.pages;

import com.oracle.auto.mobile.screen.ScreenFactoryBase;
import org.apache.log4j.Logger;

public class TestAppMobileScreenFactory extends ScreenFactoryBase {

    private static Logger log = Logger.getLogger(TestAppMobileScreenFactory.class);
    private static TestAppMobileScreenFactory obj = new TestAppMobileScreenFactory();

    private TestAppMobileScreenFactory() {
    }

    public static TestAppMobileScreenFactory getInstance() {
        return obj;
    }
}
