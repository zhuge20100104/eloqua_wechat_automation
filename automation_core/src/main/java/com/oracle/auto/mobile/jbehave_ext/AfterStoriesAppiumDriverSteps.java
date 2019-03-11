package com.oracle.auto.mobile.jbehave_ext;

import com.oracle.auto.mobile.driver.MobileDriverFactory;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.AfterStories;

public class AfterStoriesAppiumDriverSteps {
    private static Logger log = Logger.getLogger(AfterStoriesAppiumDriverSteps.class);

    AfterStoriesAppiumDriverSteps() {

    }

    @AfterStories
    public void closeAppiumSession() {
        MobileDriverFactory.instance().stopAllAppiumSessions();
    }

}
