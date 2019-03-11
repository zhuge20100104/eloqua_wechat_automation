package com.oracle.auto.web.jbehave_ext;

public interface UnitTransformer {
	boolean stateType(); // false - no state, called every time. true - story level state once a story
	String transform(String s);
}
