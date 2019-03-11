package com.oracle.auto.web.jbehave_ext;

import java.util.concurrent.ThreadLocalRandom;

public class StoryRandUnitTransformer implements UnitTransformer {

    // fixed story level randon number
    private String randonKey = "FS_RAND_NUM";
    private int upLimit = 10000;
    private int randLen = 4;

    public StoryRandUnitTransformer() {
    }

    public String transform(String s) {
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
        return true; // true means only called once per story
    }

}
