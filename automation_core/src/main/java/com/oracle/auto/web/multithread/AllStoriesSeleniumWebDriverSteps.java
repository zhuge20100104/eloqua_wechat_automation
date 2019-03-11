package com.oracle.auto.web.multithread;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.AfterStories;

import java.io.File;
import java.io.IOException;

public class AllStoriesSeleniumWebDriverSteps {
    private static Logger log = Logger.getLogger(AllStoriesSeleniumWebDriverSteps.class);

    public AllStoriesSeleniumWebDriverSteps() {
    }

    @AfterStories
    public void closeAllSelenium() {
        // stop all selenium folders
        SeleniumPageFactory.instance().stopAllSeleniumSessions();
        String downloadRoot = SeleniumPageFactory.getBrowserDownloadRoot();
        String profileRoot = SeleniumPageFactory.getBrowserProfileRoot();

        // remove all related folders of browsers
        File file;
        try {
            log.info("clean download folder root: " + downloadRoot);
            file = new File(downloadRoot);
            if (file.exists()) FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            log.error("fail to clean up download root", e);
        }

        try {
            file = new File(profileRoot);
            log.info("clean user folder root: " + profileRoot);
            if (file.exists()) FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            log.error("fail to clean up user profile root", e);
        }
    }
}
