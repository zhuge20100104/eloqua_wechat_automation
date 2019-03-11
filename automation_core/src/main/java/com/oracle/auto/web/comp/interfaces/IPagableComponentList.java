package com.oracle.auto.web.comp.interfaces;

public interface IPagableComponentList extends IComponentList, IPagableList {
	IComponent getCompInCurrentPage(String value);
	<T> T getCompAsInCurrentPage(String value, Class<T> classType);
}
