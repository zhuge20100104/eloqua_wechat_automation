package com.oracle.auto.web.jbehave_ext;


import com.oracle.auto.web.utility.PropertyConfiger;

import java.util.concurrent.ThreadLocalRandom;

public class ThreadStoryRandUnitTransformer implements UnitTransformer {

    private String randonKey = "TS_RAND_NUM";
    private int upLimit = 10000;
    private int randLen = 4;

    public ThreadStoryRandUnitTransformer() {
    }

    public String transform(String s) {
        // if thread count is 1, then return empty string directly.
        int threadCount = PropertyConfiger.instance().getEnvData("bdd.thread.count", 1);
        if (threadCount == 1)
            return "TS"; // return a fixed string, but not empty because sometimes it's enclosed in JSON "abc %TS_RAND_NUM%" and might make a space value instead

        int rand = ThreadLocalRandom.current().nextInt(upLimit);
        String ret = String.valueOf(rand);
        while (ret.length() < randLen)
            ret += "0";

        return ret;
    }

    public String getRandonKey() {
        return randonKey;
    }

    public void setRandonKey(String randonKey) {
        this.randonKey = randonKey;
    }

    public int getUpLimit() {
        return upLimit;
    }

    public void setUpLimit(int upLimit) {
        this.upLimit = upLimit;
        this.randLen = String.valueOf(upLimit - 1).length();
    }

    @Override
    public boolean stateType() {
        int threadCount = PropertyConfiger.instance().getEnvData("bdd.thread.count", 1);
        return (threadCount != 1); // true means only called once per story
    }

}
