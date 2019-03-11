package com.oracle.auto.web.comp.interfaces;

import java.util.List;

public interface IList {
    int getItemIndex(String value);

    //Gets item's occurrence in current page
    int getItemCount(String value);

    boolean isItemExist(String value);

    int getCount();

    List<String> getValues();
}
