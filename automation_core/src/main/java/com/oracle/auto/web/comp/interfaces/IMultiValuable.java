package com.oracle.auto.web.comp.interfaces;

import java.util.List;

public interface IMultiValuable extends IComponent {
    // get value from grid
    List<String> getValues();

    void setValues(List<String> values);

    void editRow(int rowIndex, String value);

    void moveUpRow(int rowIndex);

    void moveDownRow(int rowIndex);

    void removeRow(int rowIndex);

    void appendValue(String value);

    // remove all values
    void removeAll();

    // get total count
    int getTotalCount();

    // get total count of item in the list
    int getCountInList(String value);

    // get item index (only first value)
    int getValueIndex(String value);

    // check if item in list
    boolean isValueInList(String value);

}
