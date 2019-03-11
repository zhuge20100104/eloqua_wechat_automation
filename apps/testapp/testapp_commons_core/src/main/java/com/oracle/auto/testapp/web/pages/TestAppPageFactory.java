package com.oracle.auto.testapp.web.pages;

import com.oracle.auto.web.pages.object.PageFactoryBase;
import org.apache.log4j.Logger;

public class TestAppPageFactory extends PageFactoryBase {

    private static Logger log = Logger.getLogger(TestAppPageFactory.class);
    private static TestAppPageFactory obj = new TestAppPageFactory();

    private TestAppPageFactory() {
    }

    public static TestAppPageFactory getInstance() {
        return obj;
    }
}
