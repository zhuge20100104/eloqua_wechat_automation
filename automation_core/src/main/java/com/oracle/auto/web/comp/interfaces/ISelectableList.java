package com.oracle.auto.web.comp.interfaces;


public interface ISelectableList extends IList {
    void select(String value);

    int selectedIndex();

    String selectedValue();
}
