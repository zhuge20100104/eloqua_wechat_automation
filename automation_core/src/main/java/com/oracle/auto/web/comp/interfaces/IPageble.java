package com.oracle.auto.web.comp.interfaces;

public interface IPageble {
    void firstPage();

    void nextPage();

    void previousPage();

    void gotoPage(int pageNumber); // start from 1

    int getCurrentPageNumber();
}
