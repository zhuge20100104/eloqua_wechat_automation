package com.oracle.auto.web.comp.interfaces;

public interface IComponentList extends IList {
    IComponent getComp(String value);

    <T> T getCompAs(String value, Class<T> classType);
}
