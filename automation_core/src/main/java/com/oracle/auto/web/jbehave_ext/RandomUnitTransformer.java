package com.oracle.auto.web.jbehave_ext;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUnitTransformer implements UnitTransformer {

	private String randonKey = "RAND_NUM";
	private int upLimit = 10000;
	private int randLen = 4;
	
	public RandomUnitTransformer() {
		// TODO Auto-generated constructor stub
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
		this.randLen = String.valueOf(upLimit-1).length();
	}

	@Override
	public boolean stateType() {
		return false; // no state
	}

}
