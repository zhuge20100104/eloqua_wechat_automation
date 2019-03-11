package com.oracle.auto.web.comp.interfaces;

import java.util.List;

public interface IPagableList extends IList, IPageble {
	int getItemIndexInCurrentPage(String value);
	int getItemCountInCurrentPage(String value);
	boolean isItemExistInCurrentPage(String value);
	int getCountInCurrentPage();
	List<String> getValuesInCurrentPage();

}
