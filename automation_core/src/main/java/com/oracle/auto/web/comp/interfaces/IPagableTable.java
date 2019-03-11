package com.oracle.auto.web.comp.interfaces;

import java.util.List;

public interface IPagableTable extends IPageble, ITable {
	boolean isItemInAnyPage(int visibleColIndex, String value);
	int getItemIndexInAnyPage(int visibleColIndex, String value);
	List<String> getItemValuesInAllPage(int visibleColIndex);
}
