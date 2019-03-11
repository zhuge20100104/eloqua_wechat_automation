package com.oracle.auto.web.comp.interfaces;

import org.openqa.selenium.WebElement;

public interface IElementFinder {
    WebElement getElment();

    String getElementValueByClassName(String className);

    String[] getElementValuesByClassName(String className);

    String getElementValueByClassName(String className, int index);

    void clickElementValueByClassName(String className);

    void clickElementValueByClassName(String className, int index);
}
