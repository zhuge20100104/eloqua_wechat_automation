package com.oracle.auto.web.comp.interfaces;

import java.util.List;

public interface ITable {
    //
    // column/row value related operations
    //
    int getColCount();

    int getCountInCurrentPage();

    String getColHeaderText(int colIndex);

    boolean isItemInCurrentPage(int colIndex, String value);

    int getItemIndexInCurrentPage(int colIndex, String value);

    List<String> getItemValuesInCurrentPage(int colIndex);

    List<String> getDisplayedStringsByRow(int rowIndex);

    //
    // row based operations
    //
    void selectRow(int rowIndex);

    void unSelectAll();

    void contextClickRow(int rowIndex);

    //
    // row/cell operations
    //
    void clickRowCellLink(int rowIndex, int colIndex);

    void doubleClickRowCell(int rowIndex, int colIndex);

    void contextClickRow(int rowIndex, int colIndex);

    String getItemValueInCurrentPage(int rowIndex, int colIndex);
}
