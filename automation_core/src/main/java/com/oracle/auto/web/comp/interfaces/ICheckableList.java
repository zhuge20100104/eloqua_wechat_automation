package com.oracle.auto.web.comp.interfaces;

public interface ICheckableList extends IList {
	void check(String value);
	void uncheck(String value);
	void operate(String operationName);
}
